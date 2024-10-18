package br.ufal.ic.p2.myfood.gerenciador;

import br.ufal.ic.p2.myfood.banco.Dados;
import br.ufal.ic.p2.myfood.exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.exception.AtributoNaoExisteException;
import br.ufal.ic.p2.myfood.exception.UsuarioNaoEhEntregadorException;
import br.ufal.ic.p2.myfood.exception.entrega.*;
import br.ufal.ic.p2.myfood.exception.PedidoNaoEncontradoException;
import br.ufal.ic.p2.myfood.models.*;

import java.util.Map;

public class GerenteEntrega {
    private final Map<Integer, Empresa> listaEmpresa;
    private final Map<Integer, Usuario> listaUsuario;

    private static final String PRONTO = "pronto";
    private static final String ENTREGUE = "entregue";
    private static final String ENTREGANDO = "entregando";
    private static final String ENTREGADOR = "entregador";
    private static final String CLIENTE = "cliente";

    public GerenteEntrega(Dados dados) {
        this.listaEmpresa = dados.getListaEmpresa();
        this.listaUsuario = dados.getListaUsuario();
    }

    public int criaEntrega(int idPedido, int idEntregador, String destino) throws Exception {
        Pedido pedido = listaEmpresa.values().stream()
                .flatMap(e -> e.getListaPedido().values().stream())
                .filter(o -> o.getIdPedido() == idPedido)
                .filter(o -> o.getEstado().equals(PRONTO))
                .findFirst()
                .orElseThrow(PedidoNaoEstaProntoParaEntregaException::new);

        Entregador usuario = listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == idEntregador)
                .findFirst()
                .orElseThrow(NaoEhUmEntregadorValidoException::new);

        if (usuario.estaSendoEntregue()) throw new EntregadorAindaEmEntregaException();

        if (destino == null || destino.isEmpty()) {
            destino = listaUsuario.values().stream()
                    .filter(u -> u.tipoUsuario().equals(CLIENTE))
                    .map(u -> (Cliente) u)
                    .filter(u -> u.getListaPedido().values().stream()
                            .anyMatch(o -> o.getIdPedido() == idPedido))
                    .map(Cliente::getEndereco)
                    .findFirst()
                    .orElseThrow(DestinoInvalidoException::new);
        }

        Entrega entrega = new Entrega(destino, idEntregador, idPedido);
        Empresa empresa = listaEmpresa.get(pedido.getIdEmpresa());
        empresa.addEntrega(entrega);

        usuario.toggleEstaSendoEntregue();
        pedido.setEstado(ENTREGANDO);

        return entrega.getId();
    }

    public String getEntrega(int idEntrega, String atributo) throws Exception {
        if (atributo == null || atributo.isEmpty())  throw new AtributoInvalidoException();

        Entrega entrega = listaEmpresa.values().stream()
                .flatMap(e -> e.getListaEntrega().values().stream())
                .filter(e -> e.getId() == idEntrega)
                .findFirst()
                .orElseThrow(EntregaNaoEncontradaException::new);

        String n_atributo = atributo;
        return switch (n_atributo) {
            case "cliente" -> getNomeCliente(entrega);
            case "empresa" -> getNomeEmpresa(entrega);
            case "pedido" -> String.valueOf(entrega.getIdPedido());
            case "entregador" -> getNomeEntregador(entrega);
            case "destino" -> entrega.getDestino();
            case "produtos" -> getProdutos(entrega);
            default -> throw new AtributoNaoExisteException();
        };
    }

    public int getIdEntrega(int idPedido) throws Exception {
        return listaEmpresa.values().stream()
                .flatMap(c -> c.getListaEntrega().values().stream())
                .filter(d -> d.getIdPedido() == idPedido)
                .map(Entrega::getId)
                .findFirst()
                .orElseThrow(NaoExisteEntregaComEsseIdException::new);
    }

    public void entregar(int idEntrega) throws Exception {
        Entrega entrega = listaEmpresa.values().stream()
                .flatMap(e -> e.getListaEntrega().values().stream())
                .filter(d -> d.getId() == idEntrega)
                .findFirst()
                .orElseThrow(NaoExisteNadaParaEntregarExeption::new);

        Pedido pedido = listaEmpresa.values().stream()
                .flatMap(c -> c.getListaPedido().values().stream())
                .filter(o -> o.getIdPedido() == entrega.getIdPedido())
                .findFirst()
                .orElseThrow(PedidoNaoEncontradoException::new);

        pedido.setEstado(ENTREGUE);

        Entregador usuario = listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == entrega.getIdEntregador())
                .findFirst()
                .orElseThrow(UsuarioNaoEhEntregadorException::new);

        usuario.toggleEstaSendoEntregue();
    }

    private String getNomeCliente(Entrega entrega) throws AtributoNaoExisteException {
        return listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(CLIENTE))
                .map(u -> (Cliente) u)
                .filter(u -> u.getListaPedido().values().stream()
                        .anyMatch(o -> o.getIdPedido() == entrega.getIdPedido()))
                .map(Cliente::getNome)
                .findFirst()
                .orElseThrow(AtributoNaoExisteException::new);
    }

    private String getNomeEmpresa(Entrega entrega) throws AtributoNaoExisteException {
        return listaEmpresa.values().stream()
                .filter(e -> e.getListaPedido().values().stream()
                        .anyMatch(p -> p.getIdPedido() == entrega.getIdPedido()))
                .map(Empresa::getNome)
                .findFirst()
                .orElseThrow(AtributoNaoExisteException::new);
    }

    private String getNomeEntregador(Entrega entrega) throws AtributoNaoExisteException {
        return listaUsuario.values().stream()
                .filter(u -> u.tipoUsuario().equals(ENTREGADOR))
                .map(u -> (Entregador) u)
                .filter(u -> u.getId() == entrega.getIdEntregador())
                .map(Entregador::getNome)
                .findFirst()
                .orElseThrow(AtributoNaoExisteException::new);
    }

    private String getProdutos(Entrega entrega) throws AtributoNaoExisteException {
        return listaEmpresa.values().stream()
                .flatMap(e -> e.getListaPedido().values().stream())
                .filter(o -> o.getIdPedido() == entrega.getIdPedido())
                .map(Pedido::produtoInString)
                .findFirst()
                .orElseThrow(AtributoNaoExisteException::new);
    }
    
}
