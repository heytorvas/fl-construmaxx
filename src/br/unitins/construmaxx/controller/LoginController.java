package br.unitins.construmaxx.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.construmaxx.application.Session;
import br.unitins.construmaxx.application.Util;
import br.unitins.construmaxx.dao.UsuarioDAO;
import br.unitins.construmaxx.model.Usuario;

@Named
@RequestScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = -2507565400022883428L;

	private Usuario usuario;

	@SuppressWarnings({ "static-access" })
	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = dao.login(getUsuario().getLogin(), hashSenha);

		
		if (usuario != null) {
			
			//administrador
			if (usuario.getPerfil().equals(getUsuario().getPerfil().valueOf(1))) {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "templatefuncionario.xhtml?faces-redirect=true";
			}
			
			//funcionario
			if (usuario.getPerfil().equals(getUsuario().getPerfil().valueOf(2))) {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "templatefuncionario.xhtml?faces-redirect=true";
			}
			
			//cliente
			if (usuario.getPerfil().equals(getUsuario().getPerfil().valueOf(3))) {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "templatefuncionario.xhtml?faces-redirect=true";
			}
		}
		Util.addMessageError("usuario ou senha invalido.");
		return null;
	}

	public void limpar() {
		setUsuario(new Usuario());
		// usuario = new Usuario();
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
