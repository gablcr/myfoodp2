package br.ufal.ic.p2.myfood.models;

public class Farmacia extends Empresa {
    private Boolean abre24horas;
    private Integer numeroFuncionarios;

    public Farmacia(){}
    public Farmacia(String tipoEmpresa, int idDono, String nomeEmpresa, String endereco, Boolean abre24horas, Integer numeroFuncionarios) {
        super(tipoEmpresa, idDono, nomeEmpresa, endereco);
        this.abre24horas = abre24horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public Boolean getAbre24Horas() {
        return abre24horas;
    }

    //lembrar das horas

    public Integer getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setAbre24horas(Boolean abre24horas) {
        this.abre24horas = abre24horas;
    }

    public void setNumeroFuncionarios(Integer numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

}
