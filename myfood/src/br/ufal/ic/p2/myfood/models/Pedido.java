package br.ufal.ic.p2.myfood.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;



public class Pedido {
    private static int idCtd = 1;
    private int idPedido;
    private int idCliente;
    private int idEmpresa;
    private String estado;
    private float valor;

    private List<Produto> listaProduto;

    public Pedido() {}

    private static final String ABERTO = "aberto";

    public Pedido(int idCliente, int idEmpresa) {
        this.idPedido = idCtd++;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.estado = ABERTO;
        this.valor = 0;
        this.listaProduto = new ArrayList<>();
    }

    public static int getIdCtd() {
        return idCtd;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public float getValor() {
        return valor;
    }

    public static void setIdCtd(int idCtd) {
        Pedido.idCtd = idCtd;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setlistaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public void addProduto(Produto produto) {
        listaProduto.add(produto);
        valor += produto.getValor();
    }

    public void removeProduto(Produto produto) {
        for (Produto produtoAux : listaProduto) {
            if (produtoAux.equals(produto)) {
                listaProduto.remove(produtoAux);
                valor -= produtoAux.getValor();
                return;
            }
        }
    }


    public String produtoInString() {
        return listaProduto.stream()
                .sorted(Comparator.comparingInt(Produto::getId))
                .map(Produto::getNome)
                .collect(Collectors.joining(", ", "{[", "]}"));
    }
    
}







    


