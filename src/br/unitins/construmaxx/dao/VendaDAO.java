package br.unitins.construmaxx.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.unitins.construmaxx.application.Util;
import br.unitins.construmaxx.model.ItemVenda;
import br.unitins.construmaxx.model.Perfil;
import br.unitins.construmaxx.model.Usuario;
import br.unitins.construmaxx.model.Venda;

public class VendaDAO extends DAO<Venda> {

	public VendaDAO(Connection conn) {
		super(conn);
	}
	
	public VendaDAO() {
		super(null);
	}

	@Override
	public void create(Venda venda) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.venda " + " (data, idusuario) " + "VALUES " + " (?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setDate(1, Date.valueOf(venda.getData()));
		stat.setInt(2, venda.getUsuario().getId());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		venda.setId(rs.getInt("id"));
		// inserindo os itens de venda
		// compartilhando a conexao para ficar na mesma transacao
		ItemVendaDAO dao = new ItemVendaDAO(conn);
		for (ItemVenda itemVenda : venda.getListaItemVenda()) {

			// informando quem eh o pai da crianca
			itemVenda.setVenda(venda);

			// salvando no banco de dados
			dao.create(itemVenda);
		}
	}

	@Override
	public void update(Venda venda) throws SQLException {

	}

	@Override
	public void delete(int id) throws SQLException {

	}

	@Override
	public List<Venda> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " + "  v.id, " + "  v.data, " + "  u.idusuario, " + "  u.nome, " + "  u.login,  "
							+ "  u.senha, " + "  u.ativo, " + "  u.perfil, " + "  u.data " + "FROM "
							+ "  venda v, " + "  usuario u " + "WHERE " + "  v.idusuario = u.id ");
			ResultSet rs = stat.executeQuery();

			List<Venda> listaVenda = new ArrayList<Venda>();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(new Usuario());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setNome(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setSenha(rs.getString("senha"));
				venda.getUsuario().setAtivo(rs.getBoolean("ativo"));
				venda.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				venda.getUsuario().setDataAniversario(rs.getDate("data").toLocalDate());
				// e os itens de venda?!!?
				// venda.setListaItemVenda(listaItemVenda);

				listaVenda.add(venda);
			}

			if (listaVenda.isEmpty())
				return null;
			return listaVenda;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Venda> findByUsuario(int idUsuario) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  v.id, " + "  v.data, "
					+ "  u.id as idusuario, " + "  u.nome, " + "  u.login,  " + "  u.senha, " + "  u.ativo, "
					+ "  u.perfil, " + "  u.data " + "FROM " + "  venda v, " + "  usuario u "
					+ "WHERE " + "  v.idusuario = u.id AND " + "  u.id = ? ");
			stat.setInt(1, idUsuario);

			ResultSet rs = stat.executeQuery();

			List<Venda> listaVenda = new ArrayList<Venda>();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setUsuario(new Usuario());
				venda.getUsuario().setId(rs.getInt("idusuario"));
				venda.getUsuario().setNome(rs.getString("nome"));
				venda.getUsuario().setLogin(rs.getString("login"));
				venda.getUsuario().setSenha(rs.getString("senha"));
				venda.getUsuario().setAtivo(rs.getBoolean("ativo"));
				venda.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				Date data = rs.getDate("data");
				venda.getUsuario().setDataAniversario(data == null ? null : data.toLocalDate());
				ItemVendaDAO dao = new ItemVendaDAO(conn);
				venda.setListaItemVenda(dao.findByVenda(venda));

				listaVenda.add(venda);
			}

			if (listaVenda.isEmpty())
				return null;
			return listaVenda;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Venda findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  descricao, "
					+ "  valor " + "FROM " + "  venda " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Venda venda = null;

			if (rs.next()) {
				venda = new Venda();
				venda.setId(rs.getInt("id"));
			}

			return venda;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
