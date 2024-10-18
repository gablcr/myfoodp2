package br.ufal.ic.p2.myfood.exception.usuario;

public class ContaComEmailJaExisteException extends Exception{
    public ContaComEmailJaExisteException() {
        super("Conta com esse email ja existe");
    }
}
