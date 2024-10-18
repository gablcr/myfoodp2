package br.ufal.ic.p2.myfood.exception.pedido;

public class NaoPermiteDoisPedidoAbertoException extends Exception {
    public NaoPermiteDoisPedidoAbertoException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
}