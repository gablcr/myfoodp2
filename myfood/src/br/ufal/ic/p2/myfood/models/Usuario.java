package br.ufal.ic.p2.myfood.models;

public abstract class Usuario {
    private static int idCtd = 1;
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario(){}

    public Usuario(String nome, String email, String senha, String endereco){
        this.id = idCtd++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public abstract String tipoUsuario();

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getEmail(){
        return email;
    }

    public String getSenha(){
        return senha;
    }

    public String getEndereco(){
        return endereco;
    }

    public static int getIdCtd() {
        return idCtd;
    }

    public static void setIdCtd(int idCtd) {
        Usuario.idCtd = idCtd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }   

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}
