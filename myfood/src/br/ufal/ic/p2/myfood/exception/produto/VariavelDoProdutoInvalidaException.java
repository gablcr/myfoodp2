package br.ufal.ic.p2.myfood.exception.produto;

public class VariavelDoProdutoInvalidaException extends Exception{
    public VariavelDoProdutoInvalidaException(String tipo) {
        super(tipo + " invalido");
    }
}