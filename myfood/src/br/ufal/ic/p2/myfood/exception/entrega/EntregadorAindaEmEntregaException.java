package br.ufal.ic.p2.myfood.exception.entrega;

public class EntregadorAindaEmEntregaException extends Exception {
	public EntregadorAindaEmEntregaException() {
		super("Entregador ainda em entrega");
	}
}
