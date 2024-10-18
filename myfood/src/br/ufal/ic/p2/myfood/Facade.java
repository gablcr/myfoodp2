package br.ufal.ic.p2.myfood;


public class Facade {
    private final mfSistema sis;

    public Facade() throws Exception {
        this.sis = new mfSistema();
    }

    public void zerarSistema() throws Exception {
        sis.resetarSistema();
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return sis.getUsuarioAtributo(id, atributo);
    }

     public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        sis.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        sis.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        sis.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public int login(String email, String senha) throws Exception {
        return sis.login(email, senha);
    }

    public void encerrarSistema() throws Exception {
        sis.endSystem();
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws Exception {

        return sis.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws Exception {
        sis.editProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return sis.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws Exception {
        return sis.listProdutos(empresa);
    }

    public int criarEmpresa(String tipoEmpresaRestaurante, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return sis.criarEmpresa(tipoEmpresaRestaurante, dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEspresaMercado, int idDono, String nomeEmpresa, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        return sis.criarEmpresa(tipoEspresaMercado, idDono, nomeEmpresa, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresaFarmacia, int idDono, String nomeEmpresa, String endereco, Boolean aberto24Horas, int numeroFuncionarios) throws Exception {

        return sis.criarEmpresa(tipoEmpresaFarmacia, idDono, nomeEmpresa, endereco, aberto24Horas, numeroFuncionarios);
    }

    public String getEmpresasDoUsuario(int id) throws Exception {
        return sis.getUserCompanies(id);
    }

    public String getAtributoEmpresa(int id, String atributo) throws Exception {
        return sis.getEmpresaAtributo(id, atributo);
    }

    public int getIdEmpresa(int id, String nome, int indice) throws Exception {
        return sis.getIdEmpresa(id, nome, indice);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        return sis.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numeroPedido, int numeroProduto) throws Exception {
        sis.addProdutoPedido(numeroPedido, numeroProduto);
    }

    public String getPedidos(int numero, String atributo) throws Exception {
        return sis.getPedidoProdutoAtributo(numero, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        sis.closeOrder(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        sis.removeProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return sis.getOrderNumber(cliente, empresa, indice);
    }

    public void alterarFuncionamento(int idMercado, String abre, String fecha) throws Exception {
        sis.changeMarketHours(idMercado, abre, fecha);
    }

    public void cadastrarEntregador(int company, int deliveryUser) throws Exception {
        sis.registrarEntregador(company, deliveryUser);
    }

    public String getEntregadores(int empresa) throws Exception {
       return sis.getEntregadores(empresa);
    }

    public String getEmpresas(int entregador) throws Exception {
        return sis.getEmpresas(entregador);
    }

    public void liberarPedido(int pedido) throws Exception {
        sis.releaseOrder(pedido);
    }

    public int obterPedido(int entregador) throws Exception {
        return sis.getPedido(entregador);
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws Exception {
        return sis.criaEntrega(pedido, entregador, destino);
    }

    public String getEntrega(int entrega, String atributo)  throws Exception {
        return sis.getEntrega(entrega, atributo);
    }

    public int getIdEntrega(int pedido) throws Exception {
        return sis.getIdEntrega(pedido);
    }

    public void entregar(int entrega) throws Exception {
        sis.entregar(entrega);
    }


}    
