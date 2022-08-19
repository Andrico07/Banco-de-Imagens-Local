package com.mycompany.observer;

import com.mycompany.model.Usuario;


public interface IObservable {
    
    public void addObserver(IObserver observer);

    public void removeObserver(IObserver observer);

    public void notifyObservers(Usuario usuario);
}

