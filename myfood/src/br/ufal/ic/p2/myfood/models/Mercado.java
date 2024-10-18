package br.ufal.ic.p2.myfood.models;

public class Mercado extends Empresa{
    private String tipoMercado;
    private String abre;
    private String fecha;

    public Mercado() {
    }

    public Mercado(String tipoEmpresa, int idDono, String nome, String endereco, String abre, String fecha, String tipoMercado) {
        super(tipoEmpresa, idDono, nome, endereco);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public String getAbre() {
        return abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
