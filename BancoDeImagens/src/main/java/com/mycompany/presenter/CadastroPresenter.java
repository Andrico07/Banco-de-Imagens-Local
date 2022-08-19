package com.mycompany.presenter;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import com.mycompany.DAO.implement.UsuarioDAO;
import com.mycompany.model.Usuario;
import com.mycompany.observer.IObservable;
import com.mycompany.observer.IObserver;
import com.mycompany.view.CadastroView;
import java.util.ArrayList;
import java.util.List;

public class CadastroPresenter implements IObservable {

	private CadastroView cadastroView;
	private UsuarioDAO usuarioDAO;
        private final List<IObserver> observers;

	public CadastroPresenter( JDesktopPane desktop, Usuario usuario, boolean first, boolean admin ) {
            observers = new ArrayList<>();
            init( desktop, usuario, first, admin );
	}

	private void init( JDesktopPane desktop, Usuario usuario, boolean first, boolean admin ) {

		cadastroView = new CadastroView();
		usuarioDAO = new UsuarioDAO();
		desktop.add( cadastroView );
                
                cadastroView.setLocation((desktop.getWidth() - cadastroView.getWidth()) / 2, (desktop.getHeight() - cadastroView.getHeight()) / 2);

		if( usuario != null ) {

			cadastroView.getjTxtFNome().setText( usuario.getNome() );
			cadastroView.getjTxtFEmail().setText( usuario.getEmail() );
			cadastroView.getjTxtFUsername().setText( usuario.getLogin() );
			cadastroView.getjRBtnAdmin().setSelected( usuario.isAdimin() );

		}

		if( first ) {
			cadastroView.getjRBtnAdmin().setSelected( true );
			cadastroView.getjRBtnAdmin().setEnabled( false );
		} else if( !admin ) {
			cadastroView.getjRBtnAdmin().setSelected( false );
			cadastroView.getjRBtnAdmin().setEnabled( false );
		}

		cadastroView.setVisible( true );

		cadastroView.getjBtnCadastrar().addActionListener( ( e ) -> {
			if( usuario == null )
				incluir();
			else
				editar( usuario );
		} );

		cadastroView.getjBtnCancelar().addActionListener( ( e ) -> {
			cancelar();
		} );
	}

	private void incluir() {

		var nome = cadastroView.getjTxtFNome().getText();
		var email = cadastroView.getjTxtFEmail().getText();
		var nomeUsuario = cadastroView.getjTxtFUsername().getText();
		var senha = String.valueOf( cadastroView.getjPassFSenha().getPassword() );
		var admin = cadastroView.getjRBtnAdmin().isSelected();               
                
                if (nome.isBlank() || nome.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um nome de usuário");
                } else if (nomeUsuario.isBlank() || nomeUsuario.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um nome de usuário");
                } else if (email.isBlank() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um e-mail");
                } else if (senha.isBlank() || senha.isEmpty())
                    JOptionPane.showMessageDialog(cadastroView, "Insira uma senha");
                else {
                    try {
                        if( admin )
                            usuarioDAO.insert( new Usuario( nome, email, nomeUsuario, senha, true ) );
                        else
                            usuarioDAO.insert( new Usuario( nome, email, nomeUsuario, senha, false ) );
                        
                        JOptionPane.showMessageDialog( cadastroView, "Cadastro realizado com sucesso!" );
			cadastroView.dispose();
                    } catch ( Exception e ) {
                        JOptionPane.showMessageDialog( cadastroView, e.getMessage() );
                    }
                }
	}

	private void editar( Usuario usuario ) {

		var nome = cadastroView.getjTxtFNome().getText();
		var email = cadastroView.getjTxtFEmail().getText();
		var nomeUsuario = cadastroView.getjTxtFUsername().getText();
		var senha = String.valueOf( cadastroView.getjPassFSenha().getPassword() );
		var admin = cadastroView.getjRBtnAdmin().isSelected();

		if (nome.isBlank() || nome.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um nome de usuário");
                } else if (nomeUsuario.isBlank() || nomeUsuario.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um nome de usuário");
                } else if (email.isBlank() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira um e-mail");
                } else if (senha.isBlank() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(cadastroView, "Insira uma senha");
                } else {
                    try {
                        if( admin )
                            usuarioDAO.edit( new Usuario( nome, email, nomeUsuario, senha, true ) );
                        else
                            usuarioDAO.edit( new Usuario( nome, email, nomeUsuario, senha, false ) );
                        
                        JOptionPane.showMessageDialog( cadastroView, "Usuário editado com sucesso!" );
			cadastroView.dispose();
                    } catch ( Exception e ) {
                        JOptionPane.showMessageDialog( cadastroView, e.getMessage() );
                    }
                }
        }

	private void cancelar() {
		cadastroView.dispose();
	}

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Usuario usuario) {
        observers.forEach(u -> {
            u.update(usuario);
        });
    }

}
