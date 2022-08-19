package com.mycompany.model;

import com.pss.imagem.processamento.decorator.ImagemComponente;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImagemMemento extends ImagemComponente {

    public ImagemMemento(ImagemComponente imagem) throws IOException {
        this.imagem = imagem.getImagem();
    }

    @Override
    public BufferedImage getImagem() {
        return imagem;
    }

    @Override
    public ImagemComponente reverter() {
        return this;
    }

}
