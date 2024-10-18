package br.ufal.ic.p2.myfood.models;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
public abstract class Empresa {
    private static int idCtd = 1;
    private int id;
    private String tipoEmpresa;
    private int idDono;
    private String nome;
    private String endereco;

    public Empresa(){}
    private Map<Integer, Pedido> listaPedido;
    private Map<Integer, Entregador> listaEntregador;
    private Map<Integer, Produto> listaProduto;
    private Map<Integer, Entrega> listaEntrega;

    public Empresa(String tipoEmpresa, int idDono, String nome, String endereco) {
        this.id = idCtd++;
        this.tipoEmpresa = tipoEmpresa;
        this.idDono = idDono;
        this.nome = nome;
        this.endereco = endereco;
        this.listaProduto = new HashMap<>();
        this.listaPedido = new HashMap<>();
        this.listaEntregador = new HashMap<>();
        this.listaEntrega = new HashMap<>();
    }

    public static int getIdCtd() {
        return idCtd;
    }

    public int getId() {
        return id;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public int getIdDono() {
        return idDono;
    }
    
    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Map<Integer, Produto> getListaProduto() {
        return listaProduto;
    }

    public Map<Integer, Pedido> getListaPedido() {
        return listaPedido;
    }

    public Map<Integer, Entregador> getListaEntregador() {
        return listaEntregador;
    }

    public Map<Integer, Entrega> getListaEntrega() {
        return listaEntrega;
    }

    public static void setIdCtd(int idCtd) {
        Empresa.idCtd = idCtd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void setListaProduto(Map<Integer, Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public void setListaPedido(Map<Integer, Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }

    public void setListaEntregador(Map<Integer, Entregador> listaEntregador) {
        this.listaEntregador = listaEntregador;
    }

    public void setListaEntrega(Map<Integer, Entrega> listaEntrega) {
        this.listaEntrega = listaEntrega;
    }

    public void addPedido(Pedido pedido) {
        listaPedido.put(pedido.getIdPedido(), pedido);
    }

    public void addProdutos(Produto produto) {
        this.listaProduto.put(produto.getId(), produto);
    }

    public void addEntregador(Entregador entregador) {
        this.listaEntregador.put(entregador.getId(), entregador);
    }

    public void addEntrega(Entrega entrega) {
        this.listaEntrega.put(entrega.getId(), entrega);
    }

    public String produtosEmString() {
        return listaProduto.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().getNome())
                .collect(Collectors.joining(", ", "{[", "]}"));
    }

    public String EntregadoresEmString() {
        return listaEntregador.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().getEmail())
                .collect(Collectors.joining(", ", "{[", "]}"));
    }
}
