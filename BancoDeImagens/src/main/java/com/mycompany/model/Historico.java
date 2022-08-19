package com.mycompany.model;

import java.time.LocalDate;

public class Historico {

	private int id;
	private String filtro;
	private int idUsuario;
	private String path;
	private LocalDate data;

	public Historico( String filtro, int idUsuario, String path, LocalDate data ) {
		this.filtro = filtro;
		this.idUsuario = idUsuario;
		this.path = path;
		this.data = data;
	}

	public Historico( int id, String filtro, int idUsuario, String path, LocalDate data ) {
		this.id = id;
		this.filtro = filtro;
		this.idUsuario = idUsuario;
		this.path = path;
		this.data = data;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro( String filtro ) {
		this.filtro = filtro;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario( int idUsuario ) {
		this.idUsuario = idUsuario;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData( LocalDate data ) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

}
