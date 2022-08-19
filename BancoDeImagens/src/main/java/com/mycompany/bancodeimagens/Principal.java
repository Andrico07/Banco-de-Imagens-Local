package com.mycompany.bancodeimagens;

import com.mycompany.DAO.implement.PermissaoDAO;

import com.mycompany.DAO.implement.UsuarioDAO;
import com.mycompany.presenter.PrincipalPresenter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {

	public static void main( String[] args ) {
            try {
                UsuarioDAO.createTableUsuario();
                PermissaoDAO.createTablePermissoes();
                new PrincipalPresenter();
            } catch (Exception e) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
            }
	}
}
