package br.ufal.ic.p2.myfood.exception.produto;

public class ProdutoComNomeJaExisteException extends Exception {
    public ProdutoComNomeJaExisteException() {
        super("Ja existe um produto com esse nome para essa empresa");
    }
}
