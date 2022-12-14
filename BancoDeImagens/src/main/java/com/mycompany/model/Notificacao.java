package com.mycompany.model;

public class Notificacao {

	private int id;
	private Usuario usuario;
	private String mensagem;

	public Notificacao( int id, Usuario usuario, String mensagem ) {
		this.id = id;
		this.usuario = usuario;
		this.mensagem = mensagem;
	}

	public Notificacao( Usuario usuario, String mensagem ) {
		this.usuario = usuario;
		this.mensagem = mensagem;
	}

	public Notificacao() {}

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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem( String mensagem ) {
		this.mensagem = mensagem;
	}

}
