package br.ufal.ic.p2.myfood.exception.entrega;

public class EntregaNaoEncontradaException extends Exception {
    public EntregaNaoEncontradaException() {
        super("Entrega nao encontrada");
    }
}