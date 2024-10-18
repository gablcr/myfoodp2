package br.ufal.ic.p2.myfood.gerenciador;

import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.exception.*;
import br.ufal.ic.p2.myfood.exception.empresa.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.exception.pedido.*;
import br.ufal.ic.p2.myfood.models.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class GerentePedido {
    private final Map<Integer, Empresa> listaEmpresa;
    private final Map<Integer, Usuario> listaUsuario;

    private static final String ABERTO = "aberto";
    private static final String PREPARANDO = "preparando";
    private static final String PRONTO = "pronto";
    private static final String DONO = "dono";
    private static final String ENTREGADOR = "entregador";
    private static final String FARMACIA = "farmacia";

    public GerentePedido(Dados dados) {
        this.listaEmpresa = dados.getListaEmpresa();
        this.listaUsuario = dados.getListaUsuario();
    }

    public int criarPedido(int idCliente, int idEmpresa) throws Exception {
        Usuario usuario = listaUsuario.get(idCliente);
        if (usuario.tipoUsuario().equals(DONO)) throw new DonoNaoPodeCriarPedidoException();

        Cliente clienteAtual = (Cliente) usuario;
        Empresa empresaAtual = listaEmpresa.get(idEmpresa);

        validaEmpresa(empresaAtual);

        boolean hasOpenOrder = clienteAtual.getListaPedido().values().stream()
                .anyMatch(Pedido -> Pedido.getIdEmpresa() == idEmpresa && Pedido.getEstado().equals(ABERTO));

        if (hasOpenOrder) throw new NaoPermiteDoisPedidoAbertoException();

        Pedido pedido = new Pedido(idCliente, idEmpresa);
        empresaAtual.addPedido(pedido);
        clienteAtual.addPedido(pedido);

        return pedido.getIdPedido();
    }

    public int getOrderNumber(int idCliente, int idEmpresa, int index) throws Exception {
        Usuario usuario = listaUsuario.get(idCliente);
        if (usuario.tipoUsuario().equals(DONO)) throw new DonoNaoPodeCriarPedidoException();

        Cliente clienteAtual = (Cliente) usuario;
        Empresa empresaAtual = listaEmpresa.get(idEmpresa);

        validaEmpresa(empresaAtual);

        List<Pedido> ordersUser = clienteAtual.getListaPedido().values().stream()
                .filter(order -> order.getIdEmpresa() == idEmpresa)
                .toList();

        if (ordersUser.isEmpty()) throw new PedidoNaoEncontradoException();
        if (ordersUser.size() <= index) throw new IndiceMaiorQueEsperadoException();

        return ordersUser.get(index).getIdPedido();
    }

    public void addProdutoPedido(int idPedido, int idProduto) throws Exception {
        Pedido pedido = listaEmpresa.values().stream()
                .flatMap(empresa -> empresa.getListaPedido().values().stream())
                .filter(p -> p.getIdPedido() == idPedido)
                .findFirst()
                .orElseThrow(NaoExistePedidoAbertoException::new);

        if (pedido.getEstado().equals(PREPARANDO)) throw new NaoPermiteAdicionarProdutoException();
        if (!pedido.getEstado().equals(ABERTO)) throw new PedidoNaoEncontradoException();

        Empresa empresa = listaEmpresa.values().stream()
                .filter(c -> c.getListaPedido().containsValue(pedido))
                .findFirst()
                .orElseThrow(PedidoNaoEncontradoException::new);

        Produto productAux = empresa.getListaProduto().values().stream()
                .filter(p -> p.getId() == idProduto)
                .findFirst()
                .orElseThrow(ProdutoNaoPertenceEmpresaException::new);

        pedido.addProduto(productAux);
    }

    public String getPedidoProdutoAtributo(int idPedido, String atributo) throws Exception {
        if (atributo == null || atributo.isEmpty()) throw new AtributoInvalidoException();

        Pedido companyOrder = findOrder(idPedido, true);

        String n_atributo = atributo;
        return switch (n_atributo) {
            case "estado" ->companyOrder.getEstado();
            case "valor" -> String.format("%.2f", companyOrder.getValor()).replace(",", ".");
            case "cliente" -> listaUsuario.get(companyOrder.getIdCliente()).getNome();
            case "empresa" -> listaEmpresa.get(companyOrder.getIdEmpresa()).getNome();
            case "produtos" -> companyOrder.produtoInString();
            default -> throw new AtributoNaoExisteException();
        };
    }

    public void closeOrder(int idPedido) throws Exception {
        Pedido companyOrder = findOrder(idPedido, true);
        Pedido userOrder = findOrder(idPedido, false);

        companyOrder.setEstado(PREPARANDO);
        userOrder.setEstado(PREPARANDO);
    }

    public void removeProdutoPedido(int idPedido, String nameProduct) throws Exception {
        if (nameProduct == null || nameProduct.isEmpty()) throw new ProdutoInvalidoException();
        Pedido companyOrder = findOrder(idPedido, true);

        if (companyOrder.getEstado().equals(PREPARANDO)) throw new NaoPermiteRemoverEmPedidoFechadoException();
        if (!companyOrder.getEstado().equals(ABERTO)) throw new PedidoNaoEncontradoException();

        Produto productToRemove = companyOrder.getListaProduto().stream()
                .filter(product -> product.getNome().equals(nameProduct))
                .findFirst()
                .orElseThrow(ProdutoNaoEncontradoException::new);

        companyOrder.removeProduto(productToRemove);
    }

    public void releaseOrder(int idPedido) throws Exception {
        Pedido companyOrder = findOrder(idPedido, true);
        Pedido userOrder = findOrder(idPedido, false);

        if (companyOrder.getEstado().equals(PRONTO) || userOrder.getEstado().equals(PRONTO))
            throw new PedidoJaLiberadoException();
        if (!companyOrder.getEstado().equals(PREPARANDO) || !userOrder.getEstado().equals(PREPARANDO))
            throw new NaoEhPossivelLiberarException();

        companyOrder.setEstado(PRONTO);
        userOrder.setEstado(PRONTO);
    }

    public int getPedido(int idEntregador) throws Exception {
        Entregador usuario = listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == idEntregador)
                .findFirst()
                .orElseThrow(UsuarioNaoEhEntregadorException::new);

        List<Pedido> pedidos = new ArrayList<>();
        for (Empresa empresa : usuario.getListaEmpresa()) {
            for (Pedido pedido : empresa.getListaPedido().values()) {
                if (empresa.getTipoEmpresa().equals(FARMACIA) && pedido.getEstado().equals(PRONTO)) {
                    pedidos.add(pedido);
                }
            }
        }

        for (Empresa empresa : usuario.getListaEmpresa()) {
            for (Pedido pedido : empresa.getListaPedido().values()) {
                if (!empresa.getTipoEmpresa().equals(FARMACIA) && pedido.getEstado().equals(PRONTO)) {
                    pedidos.add(pedido);
                }
            }
        }

        if (usuario.getListaEmpresa().isEmpty()) throw new EntregadorNaoEstarEmNenhumaEmpresaException();
        if (pedidos.isEmpty()) throw new NaoExistePedidoParaEntregaException();

        return pedidos.getFirst().getIdPedido();
    }

    private void validaEmpresa(Empresa empresa) throws Exception {
        if (empresa == null) throw new EmpresaNaoEncontradaException();
    }

    private Pedido findOrder(int idPedido, boolean isCompanyOrder) throws Exception {
        if (isCompanyOrder) {
           return listaUsuario.values().stream()
                    .filter(usuario-> !usuario.tipoUsuario().equals(DONO))
                    .map(usuario -> (Cliente) usuario)
                    .flatMap(usuario -> usuario.getListaPedido().values().stream())
                    .filter(o -> o.getIdPedido() == idPedido)
                    .findFirst()
                    .orElseThrow(PedidoNaoEncontradoException::new);
        } else {
            return listaEmpresa.values().stream()
                    .flatMap(company -> company.getListaPedido().values().stream())
                    .filter(o -> o.getIdPedido() == idPedido)
                    .findFirst()
                    .orElseThrow(PedidoNaoEncontradoException::new);
        }
    }
    
}

