package br.ufal.ic.p2.myfood.models;

import java.util.HashMap;
import java.util.Map;


public class Cliente extends Usuario {
    private Map<Integer, Pedido> listaPedido;

    public Cliente(){}

    public Cliente(String nome, String email, String senha, String endereco){
        super(nome, email, senha, endereco);
        listaPedido = new HashMap<>();
    }

    @Override
    public String tipoUsuario() {
        return "cliente";
    }

    public Map<Integer, Pedido> getListaPedido() {
        return listaPedido;
    }

    public void addPedido(Pedido pedido) {
        listaPedido.put(pedido.getIdPedido(), pedido);
    }

    public void set(Map<Integer, Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }

}
