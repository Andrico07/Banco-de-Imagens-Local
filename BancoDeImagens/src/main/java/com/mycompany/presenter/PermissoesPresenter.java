package com.mycompany.presenter;

import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mycompany.DAO.implement.PermissaoDAO;
import com.mycompany.model.Permissao;
import com.mycompany.model.Usuario;
import com.mycompany.utils.ImagemChooser;
import com.mycompany.view.PermissoesView;

public class PermissoesPresenter {

	private Usuario usuario;
	private PermissoesView permissoesView;
	private PermissaoDAO permissaoDAO;
	private String visualizarPath = null;
	private String excluirPath = null;
	private String compartilharPath = null;
	private String aplicarPath = null;

	public PermissoesPresenter( JDesktopPane desktop, Usuario usuario ) {
		init( desktop, usuario );
	}

	private void init( JDesktopPane desktop, Usuario usuario ) {

		this.usuario = usuario;
		permissoesView = new PermissoesView();
		permissaoDAO = new PermissaoDAO();
		desktop.add( permissoesView );
		permissoesView.getLblUsuario().setText( usuario.getLogin() );

		permissoesView.setVisible( true );

		permissoesView.getjCkVisualizar().addActionListener( ( e ) -> {
			visualizar();
		} );

		permissoesView.getjCkExcluir().addActionListener( ( e ) -> {
			excluir();
		} );

		permissoesView.getjCkCompartilhar().addActionListener( ( e ) -> {
			compartilhar();
		} );

		permissoesView.getjCkAplicarFiltro().addActionListener( ( e ) -> {
			aplicarFiltro();
		} );

		permissoesView.getjBtnSalvar().addActionListener( ( e ) -> {
			salvar();
		} );

		permissoesView.getjBtnCancelar().addActionListener( ( e ) -> {
			cancelar();
		} );
	}

	private void visualizar() {

		if( permissoesView.getjCkVisualizar().isSelected() ) {
			try {

				ImagemChooser imagemChooser = new ImagemChooser();
				imagemChooser.setDialogTitle( "Selecione a imagem" );
				imagemChooser.setFileSelectionMode( ImagemChooser.FILES_ONLY );
				imagemChooser.setFileFilter( new FileNameExtensionFilter( "Imagem", "jpg" ) );
				var retorno = imagemChooser.showOpenDialog( permissoesView );

				if( retorno == JFileChooser.APPROVE_OPTION ) {
					File file = imagemChooser.getSelectedFile();
					visualizarPath = file.getPath();
				} else
					permissoesView.getjCkVisualizar().setSelected( false );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( permissoesView, "Erro ao abrir imagem" + e.getMessage() );
			}
		}
	}

	private void excluir() {

		if( permissoesView.getjCkExcluir().isSelected() ) {
			try {

				ImagemChooser imagemChooser = new ImagemChooser();
				imagemChooser.setDialogTitle( "Selecione a imagem" );
				imagemChooser.setFileSelectionMode( ImagemChooser.FILES_ONLY );
				imagemChooser.setFileFilter( new FileNameExtensionFilter( "Imagem", "jpg" ) );
				var retorno = imagemChooser.showOpenDialog( permissoesView );

				if( retorno == JFileChooser.APPROVE_OPTION ) {
					File file = imagemChooser.getSelectedFile();
					excluirPath = file.getPath();
				} else
					permissoesView.getjCkExcluir().setSelected( false );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( permissoesView, "Erro ao abrir imagem" + e.getMessage() );
			}
		}
	}

	private void compartilhar() {

		if( permissoesView.getjCkCompartilhar().isSelected() ) {
			try {

				ImagemChooser imagemChooser = new ImagemChooser();
				imagemChooser.setDialogTitle( "Selecione a imagem" );
				imagemChooser.setFileSelectionMode( ImagemChooser.FILES_ONLY );
				imagemChooser.setFileFilter( new FileNameExtensionFilter( "Imagem", "jpg" ) );
				var retorno = imagemChooser.showOpenDialog( permissoesView );

				if( retorno == JFileChooser.APPROVE_OPTION ) {
					File file = imagemChooser.getSelectedFile();
					compartilharPath = file.getPath();
				} else
					permissoesView.getjCkCompartilhar().setSelected( false );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( permissoesView, "Erro ao abrir imagem" + e.getMessage() );
			}
		}
	}

	private void aplicarFiltro() {

		if( permissoesView.getjCkAplicarFiltro().isSelected() ) {
			try {

				ImagemChooser imagemChooser = new ImagemChooser();
				imagemChooser.setDialogTitle( "Selecione a imagem" );
				imagemChooser.setFileSelectionMode( ImagemChooser.FILES_ONLY );
				imagemChooser.setFileFilter( new FileNameExtensionFilter( "Imagem", "jpg" ) );
				var retorno = imagemChooser.showOpenDialog( permissoesView );

				if( retorno == JFileChooser.APPROVE_OPTION ) {
					File file = imagemChooser.getSelectedFile();
					aplicarPath = file.getPath();
				} else
					permissoesView.getjCkAplicarFiltro().setSelected( false );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( permissoesView, "Erro ao abrir imagem" + e.getMessage() );
			}
		}
	}

	private void salvar() {
		try {
			if( permissoesView.getjCkVisualizar().isSelected() ) {
				var permissao = permissaoDAO.getPermissaoByIdsUsuarioImagem( new Permissao( this.usuario, visualizarPath, false, false, false, false ) );
				if( permissao == null )
					permissaoDAO.insert( new Permissao( this.usuario, visualizarPath, true, false, false, false ) );
				else {
					permissao.setVisualizar( true );
					permissaoDAO.update( permissao );
				}
			}

		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( permissoesView, "Erro ao conceder permissão de visualizar" + e.getMessage() );
		}

		try {
			if( permissoesView.getjCkExcluir().isSelected() ) {
				var permissao = permissaoDAO.getPermissaoByIdsUsuarioImagem( new Permissao( this.usuario, excluirPath, false, false, false, false ) );
				if( permissao == null )
					permissaoDAO.insert( new Permissao( this.usuario, excluirPath, false, true, false, false ) );
				else {
					permissao.setExcluir( true );
					permissaoDAO.update( permissao );
				}
			}

		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( permissoesView, "Erro ao conceder permissão de exclusão" + e.getMessage() );
		}

		try {
			if( permissoesView.getjCkCompartilhar().isSelected() ) {
				var permissao = permissaoDAO.getPermissaoByIdsUsuarioImagem( new Permissao( this.usuario, compartilharPath, false, false, false, false ) );
				if( permissao == null )
					permissaoDAO.insert( new Permissao( this.usuario, compartilharPath, false, false, true, false ) );
				else {
					permissao.setCompartilhar( true );
					permissaoDAO.update( permissao );
				}
			}

		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( permissoesView, "Erro ao conceder permissão de compartilhar" + e.getMessage() );
		}

		try {
			if( permissoesView.getjCkAplicarFiltro().isSelected() ) {
				var permissao = permissaoDAO.getPermissaoByIdsUsuarioImagem( new Permissao( this.usuario, aplicarPath, false, false, false, false ) );
				if( permissao == null )
					permissaoDAO.insert( new Permissao( this.usuario, aplicarPath, false, false, false, true ) );
				else {
					permissao.setAplicarFiltro( true );
					permissaoDAO.update( permissao );
				}
			}

		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( permissoesView, "Erro ao conceder permissão de aplicar filtros" + e.getMessage() );
		}

		permissoesView.dispose();
	}

	private void cancelar() {
		permissoesView.dispose();
	}

}
