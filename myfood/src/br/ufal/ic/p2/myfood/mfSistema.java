package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.gerenciador.*;

public class mfSistema {
    private final Dados banco = Dados.getParametro();

    private final GerenteUsuario gerenteUsuario = new GerenteUsuario(banco);
    private final GerenteEmpresa gerenteEmpresa = new GerenteEmpresa(banco);
    private final GerenteProduto gerenteProduto = new GerenteProduto(banco);
    private final GerentePedido gerentePedido = new GerentePedido(banco);
    private final GerenteEntrega gerenteEntrega = new GerenteEntrega(banco);

    public mfSistema() throws Exception {
    }

    public void resetarSistema() throws Exception {
        banco.apagarDados();
        banco.removeTodasEmpresas();
        banco.removeTodosUsuarios();
    }

    public String getUsuarioAtributo(int id, String atributo) throws Exception {
        return gerenteUsuario.getUsuarioAtributo(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        gerenteUsuario.criarCliente(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        gerenteUsuario.criarDono(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        gerenteUsuario.criarEntregador(nome, email, senha, endereco, veiculo, placa);
    }

    public int login(String email, String senha) throws Exception {
        return gerenteUsuario.signIn(email, senha);
    }

    public void endSystem() throws Exception {
        banco.salvar();
    }

    public int criarEmpresa(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, String tipoCozinha) throws Exception {
        return gerenteEmpresa.criarEmpresaRestaurante(tipoEmpresa, idDono, nomeEmpresa, endereco, tipoCozinha);
    }

    public String getUserCompanies(int idDono) throws Exception {
        return gerenteEmpresa.empresasPorDono(idDono);
    }

    public String getEmpresaAtributo(int idDono, String atributo) throws Exception {
        return gerenteEmpresa.getEmpresaAtributo(idDono, atributo);
    }

    public int getIdEmpresa(int idEmpresa, String nomeEmpresa, int index) throws Exception {
        return gerenteEmpresa.getIdEmpresa(idEmpresa, nomeEmpresa, index);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws Exception {
        return gerenteProduto.criarProduto(empresa, nome, valor, categoria);
    }

    public void editProduto(int produto, String nome, float valor, String categoria) throws Exception {
        gerenteProduto.editProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return gerenteProduto.getProdutoAtributo(nome, empresa, atributo);
    }

    public String listProdutos(int empresa) throws Exception {
        return gerenteProduto.listProdutosOfEmpresa(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        return gerentePedido.criarPedido(cliente, empresa);
    }

    public void addProdutoPedido(int idPedido, int produtoNumeros) throws Exception {
        gerentePedido.addProdutoPedido(idPedido, produtoNumeros);
    }

    public String getPedidoProdutoAtributo(int number, String atributo) throws Exception {
        return gerentePedido.getPedidoProdutoAtributo(number, atributo);
    }

    public void closeOrder(int number) throws Exception {
        gerentePedido.closeOrder(number);
    }

    public void removeProduto(int order, String produto) throws Exception {
        gerentePedido.removeProdutoPedido(order, produto);
    }

    public int getOrderNumber(int client, int empresa, int index) throws Exception {
        return gerentePedido.getOrderNumber(client, empresa, index);
    }

    public int criarEmpresa(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, String open, String close, String marketType) throws Exception {
        return gerenteEmpresa.criarEmpresaMercado(tipoEmpresa, idDono, nomeEmpresa, endereco, open, close, marketType);
    }

    public void changeMarketHours(int marketId, String open, String close) throws Exception {
        gerenteEmpresa.mudarHorasMercado(marketId, open, close);
    }

    public int criarEmpresa(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, Boolean open24Hours, int employeeCount) throws Exception {
        return gerenteEmpresa.criarEmpresaFarmacia(tipoEmpresa, idDono, nomeEmpresa, endereco, open24Hours, employeeCount);
    }

    public void registrarEntregador(int empresa, int deliveryUser) throws Exception {
        gerenteEmpresa.registrarEntregador(empresa, deliveryUser);
    }

    public String getEntregadores(int empresa) throws Exception {
        return gerenteEmpresa.getEntregadores(empresa);
    }

    public String getEmpresas(int entregador) throws Exception {
        return gerenteUsuario.getEmpresas(entregador);
    }

    public void releaseOrder(int order) throws Exception {
        gerentePedido.releaseOrder(order);
    }

    public int getPedido(int entregador) throws Exception {
        return gerentePedido.getPedido(entregador);
    }

    public int criaEntrega(int order, int entregador, String destination) throws Exception {
        return gerenteEntrega.criaEntrega(order, entregador, destination);
    }

    public String getEntrega(int deliveryId, String atributo) throws Exception {
        return gerenteEntrega.getEntrega(deliveryId, atributo);
    }

    public int getIdEntrega(int order) throws Exception {
        return gerenteEntrega.getIdEntrega(order);
    }

    public void entregar(int idDelivery) throws Exception {
        gerenteEntrega.entregar(idDelivery);
    }
    
}
