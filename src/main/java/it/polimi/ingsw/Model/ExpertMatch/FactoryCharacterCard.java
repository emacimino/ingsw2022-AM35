package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.*;
import it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards.*;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.MatchFourPlayers;
import it.polimi.ingsw.Model.FactoryMatch.MatchThreePlayers;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

public class FactoryCharacterCard extends DeckCharacterCard{

    public FactoryCharacterCard(Match match) {
        super(match);
    }

    /**
         * This method returns a specific match based on the number of players passed as parameter
         * @param nameOfCard is the name of the card
         * @return a specific card based on the name of card passed as parameter
         * @throws IllegalArgumentException if the parameter is not accepted
         */
     public CharacterCard createACharacterCard(String nameOfCard, Game game) throws IllegalArgumentException{
         switch (nameOfCard) {
             case "Archer":
                 return new Archer(game);
             case "Chef":
                 return new Chef(game);
             case "Knight":
                 return new Knight(game);
             case "Messenger":
                 return new Messenger(game);
             case "Baker":
                 return new Baker(game);
             case "Friar":
                 return new Friar(game);
             case "Jester":
                 return new Jester(game);
             case "Princess":
                 return new Princess(game);
         }
            throw new IllegalArgumentException("Please, insert a valid name");

        }
}
