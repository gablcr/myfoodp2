package br.ufal.ic.p2.myfood.exception.entrega;

public class NaoEhUmEntregadorValidoException extends Exception {
    public NaoEhUmEntregadorValidoException() {
        super("Nao e um entregador valido");
    }
}
