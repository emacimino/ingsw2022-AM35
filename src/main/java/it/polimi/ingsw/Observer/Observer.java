package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Exception.ExceptionGame;

//Interfaccia Observer ha le sue classi che osservano un oggetto e quando questo oggetto notifica un cambiamento
//le classi Observer effettuano il metodo update (chiamato dall'oggetto osservato)
public interface Observer {
    void update(Object message) throws ExceptionGame;
}
