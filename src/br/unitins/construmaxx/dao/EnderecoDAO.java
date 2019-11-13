package br.unitins.construmaxx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.construmaxx.model.Endereco;

public class EnderecoDAO extends DAO<Endereco> {

	public EnderecoDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Endereco endereco) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "endereco "
				+ " (id, logradouro, numero, cidade, estado, cep) " + "VALUES " + " (?, ?, ?, ?, ?, ?) ");

		stat.setInt(1, endereco.getId());
		stat.setString(2, endereco.getLogradouro());
		stat.setString(3, endereco.getNumero());
		stat.setString(4, endereco.getCidade());
		stat.setString(5, endereco.getEstado());
		stat.setString(6, endereco.getCep());

		stat.execute();
		stat.close();

	}

	@Override
	public void update(Endereco endereco) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE endereco SET " + " logradouro = ?, " + " numero = ?, "
				+ " cidade = ?, " + " estado = ? " + " cep = ?, " + "WHERE " + " id = ? ");
		stat.setString(1, endereco.getLogradouro());
		stat.setString(2, endereco.getNumero());
		stat.setString(3, endereco.getCidade());
		stat.setString(4, endereco.getEstado());
		stat.setString(5, endereco.getCep());
		stat.setInt(6, endereco.getId());

		stat.execute();
		stat.close();

	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM endereco WHERE id =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();

	}

	@Override
	public List<Endereco> findAll() {
		return null;
	}

	public Endereco findById(Integer id) {
		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + 
					"  id, " + "  logradouro, " + "  numero, " + "  cidade, " + "  estado, " + "  cep "
					+ "FROM " + "  endereco " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Endereco endereco = null;

			if (rs.next()) {
				endereco = new Endereco();
				endereco.setId(rs.getInt("id"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getString("numero"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCep(rs.getString("cep"));
			}

			return endereco;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
