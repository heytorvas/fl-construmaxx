package br.unitins.construmaxx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.construmaxx.model.Produto;

public class ProdutoDAO extends DAO<Produto> {
	
	public ProdutoDAO(Connection conn) {
		super(conn);
	}
	
	public ProdutoDAO() {
		super(null);
	}

	@Override
	public void create(Produto produto) throws SQLException {
		
		Connection  conn = getConnection();
			
		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " +
			    "produto " +
			    " (nome, descricao, valor) " +
				"VALUES " +
			    " (?, ?, ?) ");
		stat.setString(1, produto.getNome());
		stat.setString(2, produto.getDescricao());
		stat.setFloat(3, produto.getValor());
		
		stat.execute();
			
	}

	@Override
	public void update(Produto produto) throws SQLException {
		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"UPDATE produto SET " +
			    " nome = ?, " +
			    " descricao = ?, " +
			    " valor = ? " +
				"WHERE " +
			    " id = ? ");
		stat.setString(1, produto.getNome());
		stat.setString(2, produto.getDescricao());
		stat.setFloat(3, produto.getValor());
		stat.setInt(4, produto.getId());
			
		stat.execute();
			
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection  conn = getConnection();
		
		PreparedStatement stat = conn.prepareStatement(
				"DELETE FROM produto WHERE id = ?");
		stat.setInt(1, id);
		
		stat.execute();
	}

	@Override
	public List<Produto> findAll() {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  id, " +
					"  nome, " +
					"  descricao, " +
					"  valor " +
					"FROM " +
					"  produto ");
			ResultSet rs = stat.executeQuery();
			
			List<Produto> listaProduto = new ArrayList<Produto>();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getFloat("valor"));
				
				listaProduto.add(produto);
			}
			
			if (listaProduto.isEmpty())
				return null;
			return listaProduto;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Produto> findByNome(String nome) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  id, " +
					"  nome, " +
					"  descricao, " +
					"  valor " +
					"FROM " +
					"  produto " +
					"WHERE " +
					"  nome ilike ? ");
			
			stat.setString(1, nome == null ? "%" : "%"+nome+"%");
			ResultSet rs = stat.executeQuery();
			
			List<Produto> listaProduto = new ArrayList<Produto>();
			
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getFloat("valor"));
				
				listaProduto.add(produto);
			}
			
			if (listaProduto.isEmpty())
				return null;
			return listaProduto;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Produto findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null) 
			return null;
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  id, " +
					"  nome, " +
					"  descricao, " +
					"  valor " +
					"FROM " +
					"  produto " +
					"WHERE id = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Produto produto = null;
			
			if(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getFloat("valor"));
			}
			
			return produto;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
