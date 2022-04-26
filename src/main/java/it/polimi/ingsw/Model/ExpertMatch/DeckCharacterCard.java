package it.polimi.ingsw.Model.ExpertMatch;

import java.util.ArrayList;
import java.util.Random;

public class DeckCharacterCard {
    ArrayList<String> deckOfCharacterCards = new ArrayList<>();

    public DeckCharacterCard() {
        this.setDeckOfCharacterCards();
    }

    public void setDeckOfCharacterCards() {
        deckOfCharacterCards.add("friar");
        deckOfCharacterCards.add("knight");
        deckOfCharacterCards.add("chef");
        deckOfCharacterCards.add("merchant");
        deckOfCharacterCards.add("Archer");
        deckOfCharacterCards.add("Minstrel");
        deckOfCharacterCards.add("Princess");
        deckOfCharacterCards.add("Jester");
    }


    public String drawCharacterCard() {
      return deckOfCharacterCards.get(new Random().nextInt(0,deckOfCharacterCards.size()));
    }

}