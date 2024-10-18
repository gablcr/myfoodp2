package br.ufal.ic.p2.myfood.exception.usuario;

public class SenhaInvalidaException extends Exception {
    public SenhaInvalidaException() {
        super("Senha invalido");
    }
}