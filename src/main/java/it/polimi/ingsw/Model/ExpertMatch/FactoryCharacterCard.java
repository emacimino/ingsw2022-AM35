package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.FactoryMatch.Game;

public class FactoryCharacterCard{

     public CharacterCard createACharacterCard(String name, Game game) throws IllegalArgumentException{
         return switch (name) {
             case "Archer" -> new Archer(game, name);
             case "Chef" -> new Chef(game, name);
             case "Knight" -> new Knight(game, name);
          //   case "Messenger" -> new Messenger(game, name);
             case "Baker" -> new Baker(game, name);
             case "Princess" -> new Princess(game, name);
             case "Jester" -> new Jester(game, name);
             case "Friar" -> new Friar(game, name);
             case "Minstrel" -> new Minstrel(game, name);
             default -> throw new IllegalArgumentException("Invalid name of the card");
         };
        }
}
