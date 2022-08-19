package com.mycompany.presenter;

import com.mycompany.model.Imagem;
import com.mycompany.model.ImagemMemento;
import com.mycompany.model.Zelador;
import com.mycompany.view.AplicarFiltroView;
import com.mycompany.view.BrilhoDialog;
import com.mycompany.view.PixelarDialog;
import com.mycompany.view.RotacaoDialog;
import com.pss.imagem.processamento.decorator.AzulDecorator;
import com.pss.imagem.processamento.decorator.BrilhoDecorator;
import com.pss.imagem.processamento.decorator.EspelhadaDecorator;
import com.pss.imagem.processamento.decorator.ImagemComponente;
import com.pss.imagem.processamento.decorator.NegativaDecorator;
import com.pss.imagem.processamento.decorator.PixeladaDecorator;
import com.pss.imagem.processamento.decorator.RotacionaDecorator;
import com.pss.imagem.processamento.decorator.SepiaDecorator;
import com.pss.imagem.processamento.decorator.TomDeCinzaDecorator;
import com.pss.imagem.processamento.decorator.VerdeDecorator;
import com.pss.imagem.processamento.decorator.VermelhoDecorator;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;


public class AplicarFiltroPresenter {
    
    private AplicarFiltroView aplicarFiltroView;
    private ImagemComponente imagem;
    private Zelador zelador;
    
    

    public AplicarFiltroPresenter(JDesktopPane desktop, Imagem imagem) throws IOException {
        init(desktop, imagem);
    }
    
    private void init(JDesktopPane desktop, Imagem imagem) throws IOException{
        
        aplicarFiltroView = new AplicarFiltroView();
        zelador = new Zelador();
        this.imagem = imagem;
        desktop.add(aplicarFiltroView);
        atualizarImagem(imagem);
        aplicarFiltroView.setVisible(true);


        aplicarFiltroView.getCkbAzul().addActionListener((e) -> {
            aplicarAzul();
        });
        
        aplicarFiltroView.getCkbVerde().addActionListener((e) -> {
            aplicarVerde();
        });
        
        aplicarFiltroView.getCkbVermelha().addActionListener((e) -> {
            aplicarVermelho();
        });
        
        aplicarFiltroView.getCkbEspelhada().addActionListener((e) -> {
            aplicarEspelhado();
        });
        
        aplicarFiltroView.getCkbRotacao().addActionListener((e) -> {
            aplicarRotacao();
        });
        
        aplicarFiltroView.getCkbNegativo().addActionListener((e) -> {
            aplicarNegativo();
        });
        
        aplicarFiltroView.getCkbSepia().addActionListener((e) -> {
            aplicarSepia();
        });
        
        aplicarFiltroView.getCkbPixelar().addActionListener((e) -> {
            aplicarPixelado();
        });
        
        aplicarFiltroView.getCkbTonsCinza().addActionListener((e) -> {
            aplicarTonsCinza();
        });
        
        aplicarFiltroView.getCkbBrilho().addActionListener((e) -> {
            aplicarBrilho();
        });
        
        aplicarFiltroView.getCmbxFiltrosPreDefinidos().addActionListener((ActionEvent ae) -> {
            predefinicoes();
        });
        
        aplicarFiltroView.getjBtnDesfazer().addActionListener((e) -> {
            try {
                desfazer();
            } catch (IOException ex) {
                Logger.getLogger(AplicarFiltroPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        aplicarFiltroView.getjBtnRestaurar().addActionListener((e) -> {
            try {
                restaurar(imagem);
            } catch (IOException ex) {
                Logger.getLogger(AplicarFiltroPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        aplicarFiltroView.getjBtnFechar().addActionListener((e) -> {
            fechar();
        });
    }
    
    private void aplicarAzul() {
        try {
            if(aplicarFiltroView.getCkbAzul().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new AzulDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarVerde() {
        try {
            if(aplicarFiltroView.getCkbVerde().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new VerdeDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        } 
    }
    
    private void aplicarVermelho() {        
        try {
            if(aplicarFiltroView.getCkbVermelha().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new VermelhoDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarEspelhado() {
        try {
            if(aplicarFiltroView.getCkbEspelhada().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new EspelhadaDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarRotacao() {
        try {
            if(aplicarFiltroView.getCkbRotacao().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                RotacaoDialog dialog = new RotacaoDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
                int grau = (Integer) dialog.getjSpinner().getValue();
                imagem = new RotacionaDecorator(imagem, grau%360);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }       
    }
    
    private void aplicarNegativo() {
        try {
            if(aplicarFiltroView.getCkbNegativo().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new NegativaDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarSepia() {
        try {
            if(aplicarFiltroView.getCkbSepia().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new SepiaDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarPixelado() {
        try {
            if(aplicarFiltroView.getCkbPixelar().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                PixelarDialog dialogPixelar = new PixelarDialog(new javax.swing.JFrame(), true);
                dialogPixelar.setVisible(true);
                int pixel = (Integer) dialogPixelar.getjSpinner().getValue();
                if(pixel > 0) {
                    imagem = new PixeladaDecorator(imagem, pixel);
                    atualizarImagem(imagem);
                }
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n(O filtro pixelar só funciona caso seja o primeiro a ser utilizado)\n" + e.getMessage());
        }
    }
    
    private void aplicarTonsCinza() {
        try {
            if(aplicarFiltroView.getCkbTonsCinza().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                imagem = new TomDeCinzaDecorator(imagem);
                atualizarImagem(imagem);
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void aplicarBrilho() {
        try {
            if(aplicarFiltroView.getCkbBrilho().isSelected()) {
                zelador.addMemento(new ImagemMemento(imagem));
                BrilhoDialog bdialog = new BrilhoDialog(new javax.swing.JFrame(), true);
                bdialog.setVisible(true);
                int brilho = (Integer) bdialog.getjSpinner().getValue();
                if(brilho > 0) {
                    imagem = new BrilhoDecorator(imagem, brilho);
                    atualizarImagem(imagem);
                }
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro. Restaure o padrão e tente novamente.\n" + e.getMessage());
        }
    }
    
    private void predefinicoes() {
        try {
            String filtro = aplicarFiltroView.getCmbxFiltrosPreDefinidos().getSelectedItem().toString();
            if (!filtro.isEmpty()) {
                if (filtro.startsWith("1.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new NegativaDecorator(imagem);
                    imagem = new SepiaDecorator(imagem);
                    atualizarImagem(imagem);
                } else if (filtro.startsWith("2.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new TomDeCinzaDecorator(imagem);
                    imagem = new AzulDecorator(imagem);
                    atualizarImagem(imagem);
                } else if (filtro.startsWith("3.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new VermelhoDecorator(imagem);
                    imagem = new NegativaDecorator(imagem);
                    atualizarImagem(imagem);
                } else if (filtro.startsWith("4.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new VerdeDecorator(imagem);
                    imagem = new SepiaDecorator(imagem);
                    atualizarImagem(imagem);
                } else if (filtro.startsWith("5.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new AzulDecorator(imagem);
                    imagem = new EspelhadaDecorator(imagem);
                    atualizarImagem(imagem);
                } else if (filtro.startsWith("6.")) {
                    zelador.addMemento(new ImagemMemento(imagem));
                    imagem = new VermelhoDecorator(imagem);
                    imagem = new SepiaDecorator(imagem);
                    atualizarImagem(imagem);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(aplicarFiltroView, "Erro ao aplicar filtro " + e.getMessage());
        }
    }
    
    private void desfazer() throws IOException {
        if(!zelador.getMementos().isEmpty()) {
            this.imagem = zelador.getUltimo();
            atualizarImagem(this.imagem);
        }
        
        
    }

    private void restaurar(ImagemComponente imagem) throws IOException {
        zelador.addMemento(new ImagemMemento(this.imagem));
        this.imagem = imagem;
        atualizarImagem(imagem);
        clearCkbBoxes();
    }
    
    private void fechar() {        
        aplicarFiltroView.dispose();   
    }
    
    private void clearCkbBoxes() {
        aplicarFiltroView.getCkbAzul().setSelected(false);
        aplicarFiltroView.getCkbVerde().setSelected(false);
        aplicarFiltroView.getCkbVermelha().setSelected(false);
        aplicarFiltroView.getCkbEspelhada().setSelected(false);
        aplicarFiltroView.getCkbRotacao().setSelected(false);
        aplicarFiltroView.getCkbNegativo().setSelected(false);
        aplicarFiltroView.getCkbSepia().setSelected(false);
        aplicarFiltroView.getCkbPixelar().setSelected(false);
        aplicarFiltroView.getCkbTonsCinza().setSelected(false);
        aplicarFiltroView.getCkbBrilho().setSelected(false);
    }
    
    private void atualizarImagem(ImagemComponente imagem) throws IOException {
        Image dImagem = imagem.getImagem().getScaledInstance(aplicarFiltroView.getLblImagem().getWidth(), aplicarFiltroView.getLblImagem().getHeight(), Image.SCALE_SMOOTH);
        aplicarFiltroView.getLblImagem().setIcon(new ImageIcon(dImagem));
    }
    
    
}