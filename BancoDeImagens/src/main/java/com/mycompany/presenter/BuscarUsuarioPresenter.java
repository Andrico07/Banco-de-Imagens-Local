package com.mycompany.presenter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.mycompany.DAO.implement.UsuarioDAO;
import com.mycompany.model.Usuario;
import com.mycompany.view.BuscarUsuarioView;

public class BuscarUsuarioPresenter {

	private BuscarUsuarioView buscarUsuarioView;
	private UsuarioDAO usuarioDAO;
	private List<Usuario> usuarios;

	public BuscarUsuarioPresenter( JDesktopPane desktop ) throws IOException {
		init( desktop );
	}

	private void init( JDesktopPane desktop ) {

		buscarUsuarioView = new BuscarUsuarioView();
		usuarioDAO = new UsuarioDAO();
		desktop.add( buscarUsuarioView );
		buscarUsuarioView.setVisible( true );

		buscarUsuarioView.getjBtnBuscar().addActionListener( ( e ) -> {
			try {
				buscar();
			} catch ( Exception ex ) {
				Logger.getLogger( BuscarUsuarioPresenter.class.getName() ).log( Level.SEVERE, null, ex );
			}
		} );

		buscarUsuarioView.getjBtnNovo().addActionListener( ( e ) -> {
			try {
				cadastrar( desktop );
			} catch ( IOException ex ) {
				Logger.getLogger( BuscarUsuarioPresenter.class.getName() ).log( Level.SEVERE, null, ex );
			}
		} );

		buscarUsuarioView.getjBtnVisualizar().addActionListener( ( e ) -> {
			try {
				visualizar( desktop );
			} catch ( Exception ex ) {
				Logger.getLogger( BuscarUsuarioPresenter.class.getName() ).log( Level.SEVERE, null, ex );
			}
		} );

		buscarUsuarioView.getjBtnPermissao().addActionListener( ( e ) -> {
			permissoes(desktop);
		} );
                
                buscarUsuarioView.getjBtnFechar().addActionListener( ( e ) -> {
			fechar();
		} );
	}

	private void buscar() throws Exception {

		var substr = buscarUsuarioView.getjTextField().getText();

		if( substr.isBlank() || substr.isEmpty() )
			setUsuarios( usuarioDAO.findAll() );
		else {

			var query = "";
			switch ( buscarUsuarioView.getCmbxCampo().getSelectedIndex() ) {
				case 0:
					query = "SELECT * FROM Usuario WHERE CAST(id AS VARCHAR) like CAST(? AS VARCHAR) ORDER BY nome";
					break;
				case 1:
					query = "SELECT * FROM Usuario WHERE nome LIKE ? ORDER BY nome";
					break;
				case 2:
					query = "SELECT * FROM Usuario WHERE login LIKE ? ORDER BY nome";
					break;
				default:
					throw new RuntimeException( "Campo de pesquisa inv??lido!" );
			}

			setUsuarios( usuarioDAO.search( query, substr ) );
		}
		loadTable();
	}

	private void cadastrar( JDesktopPane desktop ) throws IOException {
		new CadastroPresenter( desktop, null, false, true );
	}

	private void visualizar( JDesktopPane desktop ) throws Exception {

		var linha = buscarUsuarioView.getjTable().getSelectedRow();
		if( linha == -1 ) {
			JOptionPane.showMessageDialog( buscarUsuarioView, "?? necess??rio selecionar uma linha primeiro" );
		} else {
			var id = Integer.valueOf( buscarUsuarioView.getjTable().getValueAt( linha, 0 ).toString() );
			new VisualizarUsuarioPresenter( desktop, usuarioDAO.getById( id ) );
		}
	}
        
        private void permissoes(JDesktopPane desktop) {
            
            var linha = buscarUsuarioView.getjTable().getSelectedRow();
		if( linha == -1 ) {
			JOptionPane.showMessageDialog( buscarUsuarioView, "?? necess??rio selecionar uma linha primeiro" );
		} else {
			var id = Integer.valueOf( buscarUsuarioView.getjTable().getValueAt( linha, 0 ).toString() );
                try {
                    var usuario = usuarioDAO.getById( id );
                    if(!usuario.isAdimin())
                        new VisualizarPermissoesPresenter( desktop, usuario );
                    else
                        JOptionPane.showMessageDialog(buscarUsuarioView, "Administradores j?? possuem todas as permiss??es");
                } catch (Exception ex) {
                    Logger.getLogger(BuscarUsuarioPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
		}
	}

	private void fechar() {
		buscarUsuarioView.dispose();
	}

	private void loadTable() throws Exception {

		buscarUsuarioView.getjTable().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

		var tabela = new DefaultTableModel( new Object[][]{}, new String[]{ "Id", "Nome", "Nome de Usu??rio", "Tipo" } ) {

			@Override
			public boolean isCellEditable( final int row, final int column ) {
				return false;
			}
		};

		tabela.setNumRows( 0 );

		for( Usuario u : usuarios ) {
			var tipo = u.isAdimin() ? "Administrador" : "Usu??rio";

			tabela.addRow( new Object[]{ u.getId(), u.getNome(), u.getLogin(), tipo } );
		}

		buscarUsuarioView.getjTable().setModel( tabela );
	}

	private void setUsuarios( List<Usuario> usuarios ) {
		if( usuarios == null ) {
			throw new RuntimeException( "Lista de usu??rios nula." );
		} else {
			this.usuarios = usuarios;
		}
	}

}
