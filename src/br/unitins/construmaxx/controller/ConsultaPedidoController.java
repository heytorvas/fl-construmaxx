package br.unitins.construmaxx.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.construmaxx.model.Pedido;

@Named
@ViewScoped
public class ConsultaPedidoController implements Serializable {

	private static final long serialVersionUID = -6998638931332554108L;

	private Pedido pedido;

	public ConsultaPedidoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		pedido = (Pedido) flash.get("pedidoFlash");
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
