package com.mycompany.presenter;

import java.io.IOException;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.mycompany.DAO.implement.PermissaoDAO;
import com.mycompany.model.Permissao;
import com.mycompany.model.Usuario;
import com.mycompany.view.VisualizarPermissoesView;

public class VisualizarPermissoesPresenter {

	private VisualizarPermissoesView visualizarView;
	private PermissaoDAO permissaoDAO;
	private List<Permissao> permissoes;

	public VisualizarPermissoesPresenter( JDesktopPane desktop, Usuario usuario ) {
		init( desktop, usuario );
	}

	private void init( JDesktopPane desktop, Usuario usuario ) {

		visualizarView = new VisualizarPermissoesView();
		permissaoDAO = new PermissaoDAO();
		desktop.add( visualizarView );
		try {
			permissoes = permissaoDAO.getByUsuario( usuario );
			loadTable();
		} catch ( Exception e ) {
			JOptionPane.showMessageDialog( visualizarView, "Erro ao carregar permissoes" + e.getMessage() );
			visualizarView.dispose();
		}

		visualizarView.setVisible( true );

		visualizarView.getjBtnConceder().addActionListener( ( e ) -> {
			try {
				conceder( desktop, usuario );
			} catch ( Exception ex ) {
				JOptionPane.showMessageDialog( visualizarView, "Erro ao conceder permissoes" + ex.getMessage() );
			}
		} );

		visualizarView.getjBtnRemover().addActionListener( ( e ) -> {
			remover();
		} );

		visualizarView.getjBtnCancelar().addActionListener( ( e ) -> {
			cancelar();
		} );
	}

	private void conceder( JDesktopPane desktop, Usuario usuario ) throws IOException {
		new PermissoesPresenter( desktop, usuario );

	}

	private void remover() {

		var linha = visualizarView.getjTable().getSelectedRow();
		if( linha == -1 ) {
			JOptionPane.showMessageDialog( visualizarView, "É necessário selecionar uma linha primeiro" );
		} else {
			var id = Integer.valueOf( visualizarView.getjTable().getValueAt( linha, 0 ).toString() );

			String[] options = { "Sim", "Não" };
			int retorno = JOptionPane.showOptionDialog( visualizarView, "Tem certeza que deseja remover essa permissao? ", "Remover permissao", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options, options[1] );
			if( retorno == 0 ) {
				try {
					permissaoDAO.delete( id );
					JOptionPane.showMessageDialog( visualizarView, "Permissão removida com sucesso!" );
				} catch ( Exception e ) {
					JOptionPane.showMessageDialog( visualizarView, e.getMessage() );
				}

				visualizarView.dispose();
			}
		}

	}

	private void cancelar() {
		visualizarView.dispose();
	}

	private void loadTable() throws Exception {

		visualizarView.getjTable().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

		var tabela = new DefaultTableModel( new Object[][]{}, new String[]{ "Id", "Nome de Usuário", "Imagem", "Visualizar", "Excluir", "Compartilhar", "Aplicar Filtro" } ) {

			@Override
			public boolean isCellEditable( final int row, final int column ) {
				return false;
			}
		};

		tabela.setNumRows( 0 );

		for( Permissao p : permissoes )
			tabela.addRow( new Object[]{ p.getId(), p.getUsuario().getLogin(), p.getPath(), p.isVisualizar(), p.isExcluir(), p.isCompartilhar(), p.isAplicarFiltro() } );

		visualizarView.getjTable().setModel( tabela );
	}

}
