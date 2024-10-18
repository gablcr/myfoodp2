package br.ufal.ic.p2.myfood.gerenciador;


import java.util.Map;
import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.exception.*;
import br.ufal.ic.p2.myfood.exception.empresa.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.exception.produto.*;
import br.ufal.ic.p2.myfood.models.Empresa;
import br.ufal.ic.p2.myfood.models.Produto;


public class GerenteProduto {
    private final Map<Integer, Empresa> listaEmpresa;

    private static final String nome_novo = "Nome";
    private static final String valor_novo = "Valor";
    private static final String categoria_nova = "Categoria";

    public GerenteProduto(Dados dados) {
        this.listaEmpresa = dados.getListaEmpresa();
    }


    public int criarProduto(int idEmpresa, String nameProduto, float valor, String categoria) throws Exception {
        validateProdutoFields(nameProduto, valor, categoria);

        Empresa empresaAtual = listaEmpresa.get(idEmpresa);
        validaEmpresas(empresaAtual);

        boolean hasProdutoWithSameName = empresaAtual.getListaProduto().values().stream()
                .anyMatch(produto -> produto.getNome().equals(nameProduto));
        if (hasProdutoWithSameName) throw new ProdutoComNomeJaExisteException();

        Produto produto = new Produto(idEmpresa, nameProduto, valor, categoria);
        empresaAtual.addProdutos(produto);

        return produto.getId();
    }

    public String getProdutoAtributo(String nome, int idEmpresa, String atributo) throws Exception {
        if (nome == null || nome.isEmpty()) throw new VariavelDoProdutoInvalidaException(nome_novo);
        if (atributo == null || atributo.isEmpty()) throw new AtributoInvalidoException();

        Empresa empresaAtual = listaEmpresa.get(idEmpresa);
        validaEmpresas(empresaAtual);

        Produto produto = empresaAtual.getListaProduto().values().stream()
                .filter(productAux -> productAux.getNome().equals(nome))
                .findFirst()
                .orElseThrow(ProdutoNaoEncontradoException::new);

        String n_atributo = atributo;
        return switch (n_atributo) {
            case "nome" -> produto.getNome();
            case "valor" -> String.format("%.2f", produto.getValor()).replace(",", ".");
            case "empresa" -> listaEmpresa.get(produto.getIdEmpresa()).getNome();
            case "categoria" -> produto.getCategoria();
            default -> throw new AtributoNaoExisteException();
        };
    }

    public void editProduto(int idProduto, String nameProduto, float valor, String categoria) throws Exception {
        validateProdutoFields(nameProduto, valor, categoria);

        Produto editableProduto = listaEmpresa.values().stream()
                .flatMap(empresa -> empresa.getListaProduto().values().stream())
                .filter(produto -> produto.getId() == idProduto)
                .findFirst()
                .orElseThrow(ProdutoNaoCadastradoException::new);

        editableProduto.setNome(nameProduto);
        editableProduto.setValor(valor);
        editableProduto.setCategoria(categoria);
    }

    public String listProdutosOfEmpresa(int idEmpresa) throws Exception {
        Empresa empresaAtual = listaEmpresa.get(idEmpresa);
        validaEmpresas(empresaAtual);

        return empresaAtual.produtosEmString();
    }

    private void validateProdutoFields(String name, float value, String categoria) throws Exception {
        if (name == null || name.isEmpty()) throw new VariavelDoProdutoInvalidaException(nome_novo);
        if (value <= 0) throw new VariavelDoProdutoInvalidaException(valor_novo);
        if (categoria == null || categoria.isEmpty()) throw new VariavelDoProdutoInvalidaException(categoria_nova);
    }

    private void validaEmpresas(Empresa empresa) throws Exception {
        if (empresa == null) throw new EmpresaNaoEncontradaException();
    }
    
}
