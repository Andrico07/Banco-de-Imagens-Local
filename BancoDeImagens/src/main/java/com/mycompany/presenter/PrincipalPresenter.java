package com.mycompany.presenter;

import com.mycompany.DAO.implement.NotificacaoDAO;
import com.mycompany.DAO.implement.PermissaoDAO;
import com.mycompany.DAO.implement.UsuarioDAO;
import com.mycompany.model.Imagem;
import com.mycompany.model.Usuario;
import com.mycompany.observer.IObserver;
import com.mycompany.utils.ImagemChooser;
import com.mycompany.view.PrincipalView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PrincipalPresenter implements IObserver {
    
    private PrincipalView principalView;
    private UsuarioDAO usuarioDAO;
    private PermissaoDAO permissaoDAO;
    private NotificacaoDAO notificacaoDAO;
    private Usuario usuario = null;
    
    

    public PrincipalPresenter() throws IOException {
        init();
    }
    
    private void init(){
        
        principalView = new PrincipalView();
        usuarioDAO = new UsuarioDAO();
        permissaoDAO = new PermissaoDAO();
        principalView.setVisible(true);
        
        stateOFF();
        
        if(this.usuario == null)
            new LoginPresenter(principalView.getjDesktopPane()).addObserver(this);
        
        
        principalView.getjMenuCadastrar().addActionListener((e) -> {
            try {
                cadastrar();
            } catch (IOException ex) {
                Logger.getLogger(PrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
                
        principalView.getjMenuBuscar().addActionListener((e) -> {
            try {
                buscar();
            } catch (IOException ex) {
                Logger.getLogger(PrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 


        principalView.getjAbrirImagem().addActionListener((e) -> {
            try {
                abrirImagem();
            } catch (IOException ex) {
                Logger.getLogger(PrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(PrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private void cadastrar() throws IOException {
        new CadastroPresenter(principalView.getjDesktopPane(), null, false, true);   
    }
    
    private void buscar() throws IOException {  
        new BuscarUsuarioPresenter(principalView.getjDesktopPane());       
    }
    
    private void abrirImagem() throws IOException, InterruptedException {
        
        ImagemChooser imagemChooser = new ImagemChooser();
        imagemChooser.setDialogTitle("Selecione a imagem");
        imagemChooser.setFileSelectionMode(ImagemChooser.FILES_ONLY);
        imagemChooser.setFileFilter(new FileNameExtensionFilter("Imagem", "jpg"));
        var retorno = imagemChooser.showOpenDialog(principalView);
        
        if(retorno == JFileChooser.APPROVE_OPTION) {
            
            File file = imagemChooser.getSelectedFile();
            var imagem = new Imagem(file.getPath().toString());
            
            if(this.usuario.isAdimin()) {
                new VisualizarImagemPresenter(principalView.getjDesktopPane(), imagem, this.usuario);
            } else {
                try {
                    var permissao = permissaoDAO.getPermissao(this.usuario, imagem);
                    if(permissao != null && permissao.isVisualizar())
                        new VisualizarImagemPresenter(principalView.getjDesktopPane(), imagem, this.usuario);
                    else
                        solicitarPermissaoVisualizar(imagem);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(principalView, e.getMessage());
                }
            }
            
        }
        
    }
    
    private void stateAdmON() {
        
        principalView.getjAbrirImagem().setVisible(true);
        principalView.getJMenuBar().setVisible(true);
        principalView.getBtnNotificacao().setVisible(true);
        principalView.getLblUsuarioLogado().setVisible(true);
        
    }
    
    private void stateUsuarioON() {
        
        principalView.getjAbrirImagem().setVisible(true);
        principalView.getJMenuBar().setVisible(true);
        principalView.getBtnNotificacao().setVisible(true);
        principalView.getLblUsuarioLogado().setVisible(true);
        principalView.getjMenuUsuario().setEnabled(false);
        
    }
    
    private void stateOFF() {
        
        principalView.getjAbrirImagem().setVisible(false);
        principalView.getJMenuBar().setVisible(false);
        principalView.getBtnNotificacao().setVisible(false);
        principalView.getLblUsuarioLogado().setVisible(false);
        
    }

    @Override
    public void update(Usuario usuario) {
        
        if(usuario == null) {
            stateOFF();
        } else {
            this.usuario = usuario;
            if(usuario.isAdimin()) {
                stateAdmON();
                principalView.getLblUsuarioLogado().setText(usuario.getNome() + " - Administrador");
            } else {
                stateUsuarioON();
                principalView.getLblUsuarioLogado().setText(usuario.getNome() + " - Usuário");
            }
        }
        
        
    }
    
    private void solicitarPermissaoVisualizar(Imagem imagem) {
        String[] options = { "Sim", "Não" };
		int retorno = JOptionPane.showOptionDialog(
                        principalView, 
                        "Você não possui permissão para visualizar esta imagem.\nDeseja solicitar permissão a um Administrador?", 
                        "Solicitar acesso", 
                        JOptionPane.YES_OPTION, 
                        JOptionPane.NO_OPTION, 
                        null, 
                        options, 
                        options[1] 
                );
		if( retorno == 0 ) {
			/*try {
                                notificacaoDAO.insert());
				JOptionPane.showMessageDialog( principalView, "Solicitacao enviada" );
			} catch ( Exception e ) {
				JOptionPane.showMessageDialog( principalView, e.getMessage() );
			}*/
                        
                        JOptionPane.showMessageDialog( principalView, "Solicitacao enviada" );
		}
    }
        
    
    
}
