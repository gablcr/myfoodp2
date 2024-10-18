package br.ufal.ic.p2.myfood.exception.empresa;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super("Empresa nao encontrada");
    }
}