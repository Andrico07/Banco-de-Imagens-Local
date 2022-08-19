package com.mycompany.model;

public class Permissao {

	private int id;
	private Usuario usuario;
	private String path;
	private boolean visualizar;
	private boolean excluir;
	private boolean compartilhar;
	private boolean aplicarFiltro;

	public Permissao( int id, Usuario usuario, String path, boolean visualizar, boolean excluir, boolean compartilhar, boolean aplicarFiltro ) {
		this.id = id;
		this.usuario = usuario;
		this.path = path;
		this.visualizar = visualizar;
		this.excluir = excluir;
		this.compartilhar = compartilhar;
		this.aplicarFiltro = aplicarFiltro;
	}
        
        public Permissao( Usuario usuario, String path, boolean visualizar, boolean excluir, boolean compartilhar, boolean aplicarFiltro ) {
		this.usuario = usuario;
		this.path = path;
		this.visualizar = visualizar;
		this.excluir = excluir;
		this.compartilhar = compartilhar;
		this.aplicarFiltro = aplicarFiltro;
	}

	public Permissao() {}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario( Usuario usuario ) {
		this.usuario = usuario;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar( boolean visualizar ) {
		this.visualizar = visualizar;
	}

	public boolean isExcluir() {
		return excluir;
	}

	public void setExcluir( boolean excluir ) {
		this.excluir = excluir;
	}

	public boolean isCompartilhar() {
		return compartilhar;
	}

	public void setCompartilhar( boolean compartilhar ) {
		this.compartilhar = compartilhar;
	}

	public boolean isAplicarFiltro() {
		return aplicarFiltro;
	}

	public void setAplicarFiltro( boolean aplicarFiltro ) {
		this.aplicarFiltro = aplicarFiltro;
	}
}
