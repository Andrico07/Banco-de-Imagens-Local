package com.mycompany.model;

import com.pss.imagem.processamento.decorator.ImagemComponente;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Imagem extends ImagemComponente {
    
    private String path;

    public Imagem(String caminhoArquivo) throws IOException, InterruptedException {
        try {
            if (!caminhoArquivo.toLowerCase().endsWith(".jpg")) {
                throw new IllegalArgumentException("Informe um arquivo JPG");
            }
            imagem = ImageIO.read(new File(caminhoArquivo));
            this.path = caminhoArquivo;

        } catch (IOException e) {
            throw new IOException("Falha: Não foi possivel abrir a imagem \n" + e.getMessage());
        }
    }

    @Override
    public BufferedImage getImagem() {
        return imagem;
    }

    @Override
    public ImagemComponente reverter() {
        return this;
    }

    public String getPath() {
        return path;
    }
    
    public void restaurar(ImagemMemento memento) {
        if(memento != null) {
            this.imagem = memento.getImagem();
        }
    }

}
