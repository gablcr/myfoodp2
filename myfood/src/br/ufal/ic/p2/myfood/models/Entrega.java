package br.ufal.ic.p2.myfood.models;

public class Entrega {

    private static int idCtd = 1;
    private int id;
    private int idPedido;
    private int idEntregador;
    private String destino;

    public Entrega() {
    }

    public Entrega(String destino, int idEntregador, int idPedido) {
        this.id = idCtd++;
        this.destino = destino;
        this.idEntregador = idEntregador;
        this.idPedido = idPedido;
    }

    public static int getIdCtd() {
        return idCtd;
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdEntregador() {
        return idEntregador;
    }

    public String getDestino() {
        return destino;
    }

    public static void setIdCtd(int idCtd) {
        Entrega.idCtd = idCtd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
}
