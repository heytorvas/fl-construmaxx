package br.unitins.construmaxx.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class TemplateController implements Serializable {
	private static final long serialVersionUID = 2094946522521114529L;

	public static String redProduto() {
		return "produto.xhtml?faces-redirect=true";
	}

	public static String redPedido() {
		return "login.xhtml?faces-redirect=true";
	}

	public static String redCarrinho() {
		return "carrinho.xhtml?faces-redirect=true";
	}

	public static String redCadastro() {
		return "usuario.xhtml?faces-redirect=true";
	}

	public static String redLogin() {
		return "login.xhtml?faces-redirect=true";
	}

}
