package br.ufal.ic.p2.myfood.exception.empresa;

public class UsuarioNaoPodeCriarEmpresaException extends Exception {
    public UsuarioNaoPodeCriarEmpresaException() {
        super("Usuario nao pode criar uma empresa");
    }
}
