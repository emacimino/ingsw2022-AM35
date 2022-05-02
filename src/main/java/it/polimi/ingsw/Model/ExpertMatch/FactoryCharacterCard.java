package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.FactoryMatch.Game;

public class FactoryCharacterCard{

     public CharacterCard createACharacterCard(String name, Game game) throws IllegalArgumentException{
         switch (name) {
             case "Archer":
                 return new Archer(game, name);
             case "Chef":
                 return new Chef(game, name);
             case "Knight":
                 return new Knight(game, name);
             case "Messenger":
                 return new Messenger(game, name);
             case "Baker":
                 return new Baker(game, name);
             case "Princess":
                 return new Princess(game, name);
             case "Jester":
                 return new Jester(game, name);
             case "Friar":
                 return new Friar(game, name);

             default:
                 throw new IllegalArgumentException("Invalid name of the card");
         }
        }
}
