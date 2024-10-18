package br.ufal.ic.p2.myfood.exception.pedido;

public class PedidoJaLiberadoException extends Exception {
	public PedidoJaLiberadoException() {
		super("Pedido ja liberado");
	}
}
