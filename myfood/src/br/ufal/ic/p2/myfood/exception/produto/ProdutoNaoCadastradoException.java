package br.ufal.ic.p2.myfood.exception.produto;

public class ProdutoNaoCadastradoException extends Exception {
    public ProdutoNaoCadastradoException() {
        super("Produto nao cadastrado");
    }
}