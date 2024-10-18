package br.ufal.ic.p2.myfood.exception.empresa;

public class EmpresaNaoCadastradaException extends Exception{
    public EmpresaNaoCadastradaException() {
        super("Empresa nao cadastrada");
    }
}
