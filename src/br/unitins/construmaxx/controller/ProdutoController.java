package br.unitins.construmaxx.controller;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.construmaxx.application.Util;
import br.unitins.construmaxx.dao.DAO;
import br.unitins.construmaxx.dao.ProdutoDAO;
import br.unitins.construmaxx.model.Produto;

@Named
@ViewScoped
public class ProdutoController implements Serializable {

	private static final long serialVersionUID = -6521198943457165212L;

	private Produto produto;

	public ProdutoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		produto = (Produto) flash.get("produtoFlash");
	}

	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		try {
			dao.create(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusao realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o produto no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		try {
			dao.update(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteracao realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o produto no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void excluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getProduto().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusao realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o produto no Banco de Dados.");
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto Produto) {
		this.produto = Produto;
	}

	public void limpar() {
		produto = null;
	}

}
