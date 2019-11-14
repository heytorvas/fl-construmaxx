package br.unitins.construmaxx.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.construmaxx.application.Session;
import br.unitins.construmaxx.application.Util;
import br.unitins.construmaxx.dao.VendaDAO;
import br.unitins.construmaxx.model.Usuario;
import br.unitins.construmaxx.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable {

	private static final long serialVersionUID = -1813598646753723644L;

	private List<Venda> listaVenda = null;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			listaVenda = dao.findByUsuario(usuario.getId());
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
			dao.closeConnection();
		}
		return listaVenda;
	}

	public void detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("detalheVenda", venda);
		Util.redirect("detalhesvenda.xhtml");
	}

}
