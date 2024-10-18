package br.ufal.ic.p2.myfood.models;

public class Restaurante extends Empresa{
    private String cozinho;
    public Restaurante() {}

    public Restaurante(String tipoEmpresa, int idDono, String nome, String endereco, String cozinho) {
        super(tipoEmpresa, idDono, nome, endereco);
        this.cozinho = cozinho;
    }

    public String getCozinho() {
        return cozinho;
    }

    public void setCozinho(String cozinho) {
        this.cozinho = cozinho;
    }
}

