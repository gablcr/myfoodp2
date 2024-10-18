package br.ufal.ic.p2.myfood.exception.empresa;

public class EmpresaComNomeJaExisteException extends Exception {
    public EmpresaComNomeJaExisteException() {
        super("Empresa com esse nome ja existe");
    }  
}
