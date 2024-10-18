package br.ufal.ic.p2.myfood.exception.pedido;

public class ProdutoNaoPertenceEmpresaException extends Exception {
    public ProdutoNaoPertenceEmpresaException() {
        super("O produto nao pertence a essa empresa");
    }
}