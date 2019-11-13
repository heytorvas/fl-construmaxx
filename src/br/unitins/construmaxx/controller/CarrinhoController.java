package br.unitins.construmaxx.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.construmaxx.application.Session;
import br.unitins.construmaxx.model.ItemVenda;
import br.unitins.construmaxx.model.Venda;

@Named 
@ViewScoped
public class CarrinhoController implements Serializable{

	private static final long serialVersionUID = 780477496476930858L;

	private Venda venda;

	public Venda getVenda() {
		if (venda == null) 
			venda = new Venda();
		
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = 
				(ArrayList<ItemVenda>)Session.getInstance().getAttribute("carrinho");
		
		// adicionando os itens do carrinho na venda
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		venda.setListaItemVenda(carrinho);
		
		return venda;
	}
	
	public void remover(int idProduto) {
		
	}
	
	public void finalizar() {
		
	}

	public void setVenda(Venda venda) {
		
		this.venda = venda;
	}
	
	
	
}
