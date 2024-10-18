package br.ufal.ic.p2.myfood.exception.entrega;

public class NaoExisteNadaParaEntregarExeption extends Exception {
    public NaoExisteNadaParaEntregarExeption() {
        super("Nao existe nada para ser entregue com esse id");
    }
}
