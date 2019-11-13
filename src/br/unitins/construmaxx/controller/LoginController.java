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

	private UsuarioController user = new UsuarioController();

	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = dao.login(getUsuario().getLogin(), hashSenha);

		for (int i = 0; i < user.getListaUsuario().size(); i++) {
			if (usuario != null
					&& user.getListaUsuario().get(i).equals(dao.login(getUsuario().getLogin(), hashSenha))) {
				Session.getInstance().setAttribute("usuarioLogado", usuario);
				return "usuario.xhtml?faces-redirect=true";
//				if(getUsuario().getPerfil() == Perfil.ADMINISTRADOR) {
//					
//					return "usuario.xhtml?faces-redirect=true";
//					}
//				
//				if(getUsuario().getPerfil() == Perfil.CLIENTE) {
//					Session.getInstance().setAttribute("usuarioLogado", usuario);
//					return "templatepadrao.xhtml?faces-redirect=true";
//				}
			}
		}
		Util.addMessageError("Usuario ou Senha Invalido.");
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginController other = (LoginController) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
