package com.mycompany.model;

public class Solicitacao {

	private int id;
	private Usuario usuario;
	private Permissao permissao;
	private boolean excluir;
	private boolean visualizar;
	private boolean compartilhar;

	public Solicitacao( int id, Usuario usuario, Permissao permissao, boolean excluir, boolean visualizar, boolean compartilhar ) {
		this.id = id;
		this.usuario = usuario;
		this.permissao = permissao;
		this.excluir = excluir;
		this.visualizar = visualizar;
		this.compartilhar = compartilhar;
	}

	public Solicitacao() {}

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

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao( Permissao permissao ) {
		this.permissao = permissao;
	}

	public boolean isExcluir() {
		return excluir;
	}

	public void setExcluir( boolean excluir ) {
		this.excluir = excluir;
	}

	public boolean isVisualizar() {
		return visualizar;
	}

	public void setVisualizar( boolean visualizar ) {
		this.visualizar = visualizar;
	}

	public boolean isCompartilhar() {
		return compartilhar;
	}

	public void setCompartilhar( boolean compartilhar ) {
		this.compartilhar = compartilhar;
	}

}
