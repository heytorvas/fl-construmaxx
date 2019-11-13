package br.unitins.construmaxx.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.construmaxx.application.Util;
import br.unitins.construmaxx.dao.DAO;
import br.unitins.construmaxx.dao.UsuarioDAO;
import br.unitins.construmaxx.model.Endereco;
import br.unitins.construmaxx.model.Perfil;
import br.unitins.construmaxx.model.Telefone;
import br.unitins.construmaxx.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = -6998638931332554108L;

	private Usuario usuario;

	private List<Usuario> listaUsuario;
	
	private String nome;

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			listaUsuario = dao.findAll();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}
	
	public List<Usuario> usuarioPesquisa() {
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			//listaUsuario = dao.findAll();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		}
		return listaUsuario;
	}

	public void incluir() {
		if (validarDados()) {
			DAO<Usuario> dao = new UsuarioDAO();
			// faz a inclusao no banco de dados
			try {
				//				String hashSenha = Util.hashSHA256(getUsuario().getSenha());
				//				getUsuario().setSenha(hashSenha);

				getUsuario().setSenha(Util.hashSHA256(getUsuario().getSenha()));

				dao.create(getUsuario());
				dao.getConnection().commit();
				Util.addMessageInfo("Inclusao realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} catch (SQLException e) {
				dao.rollbackConnection();
				dao.closeConnection();
				Util.addMessageInfo("Erro ao incluir o Usuario no Banco de Dados.");
				e.printStackTrace();
			}
		}
	}

	public void alterar() {
		if (validarDados()) {
			DAO<Usuario> dao = new UsuarioDAO();
			try {
				getUsuario().setSenha(Util.hashSHA256(getUsuario().getSenha()));
				dao.update(getUsuario());
				dao.getConnection().commit();
				Util.addMessageInfo("Alteracao realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} catch (SQLException e) {
				dao.rollbackConnection();
				dao.closeConnection();
				Util.addMessageInfo("Erro ao alterar o Usuario no Banco de Dados.");
				e.printStackTrace();
			}
		}
	}

	public void excluir() {
		if (excluir(getUsuario()))
			limpar();
	}

	public boolean excluir(Usuario usuario) {
		DAO<Usuario> dao = new UsuarioDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getUsuario().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusao realizada com sucesso.");
			return true;
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o usuario no Banco de Dados.");
			e.printStackTrace();
			return false;
		} finally {
			dao.closeConnection();
		}
	}

	private boolean validarDados() {
		if (getUsuario().getSenha() == null || getUsuario().getSenha().trim().equals("")) {
			Util.addMessageError("O campo senha deve ser informado.");
			return false;
		}
		return true;
	}

	public void editar(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usu = dao.findId(usuario.getId());
		setUsuario(usu);
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());
			usuario.setEndereco(new Endereco());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void limpar() {
		usuario = null;
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
