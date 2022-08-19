package com.mycompany.model;

import java.time.LocalDate;

public class ExclusaoLogica {

	private int id;
	private int idUsuario;
	private String path;
	private LocalDate dataExclusao;

	public ExclusaoLogica( int idUsuario, String path, LocalDate dataExclusao ) {
		this.idUsuario = idUsuario;
		this.path = path;
		this.dataExclusao = dataExclusao;
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
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

	public LocalDate getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao( LocalDate dataExclusao ) {
		this.dataExclusao = dataExclusao;
	}

}
