package br.ufal.ic.p2.myfood.models;

import java.util.Map;
import java.util.HashMap;

public class Dono extends Usuario{
    private String cpf;
    private Map<Integer, Empresa> listaEmpresa;

    public Dono(){}

    public Dono(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
        listaEmpresa = new HashMap<>();
    }

    @Override
    public String tipoUsuario() {
        return "dono";
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Map<Integer, Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setlistaEmpresa(Map<Integer, Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    
}
