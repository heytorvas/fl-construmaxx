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

	private Produto Produto;

	public ProdutoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		Produto = (Produto) flash.get("produtoFlash");
	}

	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		try {
			dao.create(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o Produto no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		try {
			dao.update(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteração realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o Usuário no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void excluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getProduto().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}

	public Produto getProduto() {
		if (Produto == null) {
			Produto = new Produto();
		}
		return Produto;
	}

	public void setProduto(Produto Produto) {
		this.Produto = Produto;
	}

	public void limpar() {
		Produto = null;
	}

}
