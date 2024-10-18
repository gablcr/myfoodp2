package br.ufal.ic.p2.myfood.banco;

import br.ufal.ic.p2.myfood.models.Empresa;
import java.util.HashMap;
import java.util.Map;
import br.ufal.ic.p2.myfood.models.Usuario;

public class Dados {
    private static volatile Dados parametro;

    public Map<Integer, Usuario> listaUsuario = new HashMap<>();
    public Map<Integer, Empresa> listaEmpresa = new HashMap<>();

    public Dados() {
    }

    public static synchronized Dados getParametro() throws Exception {
        if (parametro == null) {
            parametro = Escritor.loadDados();
            if (parametro == null) parametro = new Dados();
        }
        return parametro;
    }

    public void salvar() throws Exception {
        Escritor.salvarDados(this);
    }

    public void apagarDados() throws Exception {
        Escritor.apagarDados();
    }

    public void removeTodosUsuarios() {
        listaUsuario.clear();
    }

    public void removeTodasEmpresas() {
        listaEmpresa.clear();
    }

    public static void setParametro(Dados parametro) {
        Dados.parametro = parametro;
    }

    public Map<Integer, Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(Map<Integer, Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Map<Integer, Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setlistaEmpresa(Map<Integer, Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }
}
