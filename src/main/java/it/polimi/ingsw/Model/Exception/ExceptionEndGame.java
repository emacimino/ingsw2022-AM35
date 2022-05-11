package it.polimi.ingsw.Model.Exception;

public class ExceptionEndGame extends ExceptionGame{
    public ExceptionEndGame(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ExceptionEndGame(String errorMessage) {
        super(errorMessage);
    }
}
