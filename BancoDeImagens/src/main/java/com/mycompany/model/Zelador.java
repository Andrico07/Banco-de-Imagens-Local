package com.mycompany.model;

import com.pss.imagem.processamento.decorator.ImagemComponente;
import java.util.Stack;


public class Zelador {
    
    private final Stack<ImagemComponente> mementos;

    public Zelador() {
        mementos = new Stack();
    }
    
    public void addMemento(ImagemComponente memento) {
        mementos.push(memento);
    }
    
    public ImagemComponente getUltimo() {
        if(!mementos.empty())
            return mementos.pop();
        else
            return null;
    }

    public Stack<ImagemComponente> getMementos() {
        return mementos;
    }   
    
    
}
