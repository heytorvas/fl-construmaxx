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
import br.unitins.construmaxx.model.Usuario;
import br.unitins.construmaxx.model.Venda;

public class VendaDAO extends DAO<Venda>  {

	public VendaDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Venda entity) throws SQLException {
		
	}

	@Override
	public void update(Venda entity) throws SQLException {
		
	}

	@Override
	public void delete(int id) throws SQLException {
		
	}

	@Override
	public List<Venda> findAll() {
		return null;
	}

}

