package it.polimi.ingsw.Model.FactoryMatch;

public class FactoryMatch {

    /**
     * This method returns a specific match based on the number of players passed as parameter
     * @param numOfPlayers is the number of players
     * @return a specific match based on the number of players passed as parameter
     * @throws IllegalArgumentException if the parameter is not accepted
     */
    public BasicMatch newMatch(int numOfPlayers) throws IllegalArgumentException{
        if(numOfPlayers == 2)
            return new BasicMatch();
        else if(numOfPlayers == 3)
            return new BasicMatchThreePlayers();
        else if(numOfPlayers == 4)
            return new BasicMatchFourPlayers();
        throw new IllegalArgumentException("Please, insert a number of players between 2 and 4");

    }
}
