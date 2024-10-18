package br.ufal.ic.p2.myfood.exception.pedido;

public class NaoPermiteRemoverEmPedidoFechadoException extends Exception {
    public NaoPermiteRemoverEmPedidoFechadoException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}