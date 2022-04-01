package it.polimi.ingsw.Exception;

/**
 * ExceptionGame, which extends class Exception, is thrown every time there is an irregular move in the Game
 */
public class ExceptionGame extends Exception{
    public ExceptionGame(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
    public ExceptionGame(String errorMessage){
        super(errorMessage);
    }
}
