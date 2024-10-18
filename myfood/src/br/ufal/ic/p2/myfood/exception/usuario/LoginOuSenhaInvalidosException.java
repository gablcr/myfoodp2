package br.ufal.ic.p2.myfood.exception.usuario;

public class LoginOuSenhaInvalidosException extends Exception {
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha invalidos");
    }
}