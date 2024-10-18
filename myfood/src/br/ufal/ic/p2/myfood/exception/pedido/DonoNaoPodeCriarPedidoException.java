package br.ufal.ic.p2.myfood.exception.pedido;

public class DonoNaoPodeCriarPedidoException extends Exception {
    public DonoNaoPodeCriarPedidoException() {
        super("Dono de empresa nao pode fazer um pedido");
    }
}