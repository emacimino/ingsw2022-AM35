package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Archer;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Chef;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Knight;
import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Messenger;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.MatchFourPlayers;
import it.polimi.ingsw.Model.FactoryMatch.MatchThreePlayers;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

public class FactoryCharacterCard extends DeckCharacterCard{

        /**
         * This method returns a specific match based on the number of players passed as parameter
         * @param nameOfCard is the name of the card
         * @return a specific card based on the name of card passed as parameter
         * @throws IllegalArgumentException if the parameter is not accepted
         */
     public CharacterCard useACharacterCard(String nameOfCard, StudentBag studentBag) throws IllegalArgumentException{
         switch (nameOfCard) {
             case "Archer":
                 return new Archer(studentBag);
             case "Chef":
                 return new Chef(studentBag);
             case "Knight":
                 return new Knight(studentBag);
             case "Messenger":
                 return new Messenger(studentBag);
         }
            throw new IllegalArgumentException("Please, insert a valid name");

        }
}
