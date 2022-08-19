package com.mycompany.presenter;

import com.mycompany.DAO.implement.PermissaoDAO;
import com.mycompany.model.Imagem;
import com.mycompany.model.Usuario;
import com.mycompany.utils.ImagemChooser;
import com.mycompany.view.VisualizarImagemView;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class VisualizarImagemPresenter {
    
    private VisualizarImagemView visualizarView;
    private Usuario usuario;
    private PermissaoDAO permissaoDAO;
    

    public VisualizarImagemPresenter(JDesktopPane desktop, Imagem imagem, Usuario usuario) {
        this.usuario = usuario;
        init(desktop, imagem);
    }
    
    private void init(JDesktopPane desktop, Imagem imagem) {

        visualizarView = new VisualizarImagemView();
        permissaoDAO = new PermissaoDAO();
        desktop.add(visualizarView);
        Image dImagem = imagem.getImagem().getScaledInstance(visualizarView.getLblImagem().getWidth(), visualizarView.getLblImagem().getHeight(), Image.SCALE_SMOOTH);
        visualizarView.getLblImagem().setIcon(new ImageIcon(dImagem));
        visualizarView.setVisible(true);


        visualizarView.getBtnAplicarFiltro().addActionListener((e) -> {
            try {
                aplicarFiltro(desktop, imagem);
            } catch (IOException ex) {
                Logger.getLogger(VisualizarImagemPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        visualizarView.getBtnCompartilhar().addActionListener((e) -> {
            compartilhar();
        });
        
        visualizarView.getBtnExcluir().addActionListener((e) -> {
            excluir();
        });
        
        visualizarView.getBtnExportar().addActionListener((e) -> {
            exportar(imagem);
        });
        
        visualizarView.getBtnHistorico().addActionListener((e) -> {
            historico();
        });
        
        visualizarView.getBtnFechar().addActionListener((e) -> {
            fechar();
        });
        
    }
    
    private void aplicarFiltro(JDesktopPane desktop, Imagem imagem) throws IOException {
        if(this.usuario.isAdimin())
            new AplicarFiltroPresenter(desktop, imagem);
        else {
            try {
                var permissao = permissaoDAO.getPermissao(this.usuario, imagem);
                if(permissao != null && permissao.isAplicarFiltro())
                    new AplicarFiltroPresenter(desktop, imagem);
                else
                    solicitarPermissaoAplicaFiltro(imagem);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(visualizarView, e.getMessage());
            }
        }
    }
    
    private void compartilhar() {
        /*if(this.usuario.isAdimin())
            //Compartilhar
        else {
            try {
                var permissao = permissaoDAO.getPermissao(this.usuario, imagem);
                if(permissao != null && permissao.isAplicarFiltro())
                    //Compartilhar
                else
                    solicitarPermissaoCompartilhar(imagem);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(visualizarView, e.getMessage());
            }
        }
    }*/
        
        
    }
    
    private void excluir() {
        /*if(this.usuario.isAdimin())
            //Excluir
        else {
            try {
                var permissao = permissaoDAO.getPermissao(this.usuario, imagem);
                if(permissao != null && permissao.isExcluir())
                    //Excluir
                else
                    solicitarPermissaoExcluir(imagem);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(visualizarView, e.getMessage());
            }
        }
    }*/
        
        
    }
    
    private void exportar(Imagem imagem) {
        try {
            ImagemChooser imagemChooser = new ImagemChooser();
            imagemChooser.setDialogTitle("Exportar...");
            var retorno = imagemChooser.showSaveDialog(visualizarView);
            if (retorno == JFileChooser.APPROVE_OPTION) {
                File salvar = imagemChooser.getSelectedFile();
                if (!salvar.getPath().endsWith(".jpg")) {
                    salvar = new File(salvar + ".jpg");
                }
                ImageIO.write(imagem.getImagem(), "jpg", salvar);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(visualizarView, "Erro ao exportar imagem: " + e.getMessage());
        }

    }
    
    private void historico() {
        
        
    }
    
    private void fechar() {
        visualizarView.dispose();
    }
    
    private void solicitarPermissaoAplicaFiltro(Imagem imagem) {
        String[] options = { "Sim", "Não" };
		int retorno = JOptionPane.showOptionDialog(
                        visualizarView, 
                        "Você não possui permissão para aplicar filtros a esta imagem.\nDeseja solicitar permissão a um Administrador?", 
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
                        
                        JOptionPane.showMessageDialog( visualizarView, "Solicitacao enviada" );
		}
    }
    
    
}
