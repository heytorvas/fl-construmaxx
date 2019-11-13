package br.unitins.construmaxx.dao;

import br.unitins.construmaxx.model.Perfil;
import br.unitins.construmaxx.model.Telefone;
import br.unitins.construmaxx.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO<Usuario> {

	public UsuarioDAO(Connection conn) {
		super(conn);
	}

	public UsuarioDAO() {
		// tchê papai ... cria uma nova conexao
		super(null);
	}

	public Usuario login(String login, String senha) {

		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  ativo, " + "  perfil " + "FROM " + "  public.usuario " + "WHERE login = ? AND senha = ? ");

			stat.setString(1, login);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setTelefone(new Telefone());
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void create(Usuario usuario) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.usuario "
				+ " (nome, login, senha, ativo, perfil) " + "VALUES " + " (?, ?, ?, ?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setBoolean(4, usuario.getAtivo());
		stat.setInt(5, usuario.getPerfil().getValue());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		usuario.getTelefone().setId(rs.getInt("id"));

		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.create(usuario.getTelefone());

	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.usuario SET " + " nome = ?, " + " login = ?, "
				+ " senha = ?, " + " ativo = ?, " + " perfil = ? " + "WHERE " + " id = ? ");
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setBoolean(4, usuario.getAtivo());
		stat.setInt(5, usuario.getPerfil().getValue());
		stat.setInt(6, usuario.getId());

		stat.execute();

	}

	@Override
	public void delete(int id) throws SQLException {

		Connection conn = getConnection();
		// deletando o telefone (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		TelefoneDAO dao = new TelefoneDAO(conn);
		// telefone tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo
		// do telefone.
		dao.delete(id);

		// deletando o usuario
		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.usuario WHERE id = ?");
		stat.setInt(1, id);

		stat.execute();

	}

	@Override
	public List<Usuario> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  ativo, " + "  perfil " + "FROM " + "  public.usuario ");
			ResultSet rs = stat.executeQuery();

			List<Usuario> listaUsuario = new ArrayList<Usuario>();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				listaUsuario.add(usuario);
			}

			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Usuario findId(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  ativo, " + "  perfil " + "FROM " + "  public.usuario " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(usuario.getId()));
				// caso o retorno do telefone seja nulo, instanciar um telefone
				if (usuario.getTelefone() == null)
					usuario.setTelefone(new Telefone());

			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
