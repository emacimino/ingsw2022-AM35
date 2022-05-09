package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;

import java.util.HashSet;

public class FactoryCharacterCard{

     public CharacterCard createACharacterCard(BasicMatch basicMatch, String name) throws IllegalArgumentException{
         return switch (name) {
             case "Archer" -> new Archer(basicMatch, name);
             case "Chef" -> new Chef(basicMatch, name);
             case "Knight" -> new Knight(basicMatch, name);
             case "Messenger" -> new Messenger(basicMatch, name);
             case "Baker" -> new Baker(basicMatch, name);
             case "Princess" -> new Princess(basicMatch, name);
             case "Jester" -> new Jester(basicMatch, name);
             case "Friar" -> new Friar(basicMatch, name);
             case "Minstrel" -> new Minstrel(basicMatch, name);
             case "Magician" -> new Magician(basicMatch, name);
             case "Banker" -> new Banker(basicMatch, name);
             case "Herbalist" -> new Herbalist(basicMatch, name);

             default -> throw new IllegalArgumentException("Invalid name of the card");
         };
        }
}
