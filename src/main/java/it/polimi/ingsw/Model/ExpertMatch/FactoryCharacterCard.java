package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Archer;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Chef;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Knight;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Messenger;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.MatchFourPlayers;
import it.polimi.ingsw.Model.FactoryMatch.MatchThreePlayers;

public class FactoryCharacterCard {

        /**
         * This method returns a specific match based on the number of players passed as parameter
         * @param nameOfCard is the name of the card
         * @return a specific card based on the name of card passed as parameter
         * @throws IllegalArgumentException if the parameter is not accepted
         */
        public CharacterCard useACharacterCard(String nameOfCard) throws IllegalArgumentException{
            if(nameOfCard.equals("Archer"))
                return new CharacterCard(studentBag);
            else if(nameOfCard.equals("Chef"))
                return new Chef();
            else if(nameOfCard.equals("Knight"))
                return new Knight();
            else if(nameOfCard.equals("Messenger"))
                return new Messenger();
            throw new IllegalArgumentException("Please, insert a valid name");

        }
}
