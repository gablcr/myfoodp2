package br.ufal.ic.p2.myfood.exception.entrega;

public class PedidoNaoEstaProntoParaEntregaException extends Exception {
	public PedidoNaoEstaProntoParaEntregaException() {
		super("Pedido nao esta pronto para entrega");
	}
}

