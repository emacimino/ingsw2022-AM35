package it.polimi.ingsw.Model.Exception;

/**
 * ExceptionGame, which extends class Exception, is thrown every time there is an irregular move in the Game
 */
public class ExceptionGame extends Exception{
    /**
     * Display an error message and a throwable
     * @param errorMessage write the message
     * @param err error thrown
     */
    public ExceptionGame(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
    /**
     * Display an error message
     * @param errorMessage write the message
     */
    public ExceptionGame(String errorMessage){
        super(errorMessage);
    }
}
