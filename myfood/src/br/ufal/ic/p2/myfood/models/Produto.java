package br.ufal.ic.p2.myfood.models;

public class Produto {
    private static int idCtd = 1;
    private int id;
    private int idEmpresa;
    private String nome;
    private float valor;
    private String categoria;

    public Produto(){}

    public Produto(int idEmpresa, String nome, float valor, String categoria){
        this.id = idCtd++;
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public static int getIdCtd() {
        return idCtd;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public static void setIdCtd(int idCtd) {
        Produto.idCtd = idCtd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
