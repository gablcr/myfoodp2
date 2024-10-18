package br.ufal.ic.p2.myfood.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Entregador extends Usuario {
    private String veiculo;
    private String placa;
    private boolean estaSendoEntregue;
    private List<Empresa> listaEmpresa;

    public Entregador(){}
    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
        this.estaSendoEntregue = false;
        this.listaEmpresa = new ArrayList<>();
    }

    @Override
    public String tipoUsuario() {
        return "entregador";
    }

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public boolean estaSendoEntregue() {
        return estaSendoEntregue;
    }

    public String getPlaca() {
        return placa;
    }
    
    public String getVeiculo() {
        return veiculo;
    }

    public void toggleEstaSendoEntregue() {
        estaSendoEntregue = !estaSendoEntregue;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public void setSendoEntregue(boolean sendoEntregue) {
        estaSendoEntregue = sendoEntregue;
    }

    public void addEmpresa(Empresa empresa) {
        listaEmpresa.add(empresa);
    }

    public String empresasEmString() {
        return listaEmpresa.stream()
                .sorted(Comparator.comparing(Empresa::getId))
                .map(empresa -> String.format("[%s, %s]", empresa.getNome(), empresa.getEndereco()))
                .collect(Collectors.joining(", ", "{[", "]}"));
    }
}
