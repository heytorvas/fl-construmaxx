package br.unitins.construmaxx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.construmaxx.model.ItemVenda;
import br.unitins.construmaxx.model.Produto;
import br.unitins.construmaxx.model.Venda;

public class ItemVendaDAO extends DAO<ItemVenda> {

	public ItemVendaDAO(Connection conn) {
		super(conn);
	}

	public ItemVendaDAO() {
		super(null);
	}

	@Override
	public void create(ItemVenda itemVenda) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "itemvenda " + " (valor, idvenda, idproduto) " + "VALUES " + " (?, ?, ?) ");
		stat.setFloat(1, itemVenda.getValor());
		stat.setInt(2, itemVenda.getVenda().getId());
		stat.setInt(3, itemVenda.getProduto().getId());

		stat.execute();

	}

	@Override
	public void update(ItemVenda entity) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ItemVenda> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ItemVenda> findByVenda(Venda venda) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn
					.prepareStatement("SELECT " + "  v.id, " + "  v.valor, " + "  v.idproduto, " + "  p.nome, "
							+ "  p.descricao, " + "  p.valor as valorProduto " + "FROM " + "  itemvenda v, "
							+ "  produto p " + "WHERE " + "  v.idproduto = p.id AND " + "  v.idvenda = ? ");

			stat.setInt(1, venda.getId());
			ResultSet rs = stat.executeQuery();

			List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();

			while (rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getFloat("valor"));

				Produto produto = new Produto();
				produto.setId(rs.getInt("idproduto"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getFloat("valorProduto"));

				item.setProduto(produto);

				item.setVenda(venda);

				listaItemVenda.add(item);
			}

			if (listaItemVenda.isEmpty())
				return null;
			return listaItemVenda;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
