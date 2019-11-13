package br.unitins.construmaxx.model;

import java.time.LocalDate;

public class Pedido {
	private Integer id;
	private LocalDate data;
	private String observacoes;

	public Pedido(Integer id, LocalDate data, String observacoes) {
		super();
		this.id = id;
		this.data = data;
		this.observacoes = observacoes;
	}

	public Pedido() {
		super();
	}

	public Pedido(Integer id, LocalDate data) {
		super();
//		this.listaProdutos = listaProdutos;
		this.id = id;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer idUsuario) {
		this.id = idUsuario;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}
