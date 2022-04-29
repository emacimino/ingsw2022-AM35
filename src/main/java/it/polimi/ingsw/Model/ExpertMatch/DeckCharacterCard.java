package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.FactoryMatch.Match;

import java.util.ArrayList;
import java.util.Random;

public class DeckCharacterCard{
    ArrayList<String> deckOfCharacterCards = new ArrayList<>();
    Match match;

    public DeckCharacterCard(Match match) {
        this.match = match;
        this.setDeckOfCharacterCards();
    }

    public void setDeckOfCharacterCards() {
        deckOfCharacterCards.add("friar");
        deckOfCharacterCards.add("knight");
        deckOfCharacterCards.add("chef");
        deckOfCharacterCards.add("Archer");
        deckOfCharacterCards.add("Princess");
        deckOfCharacterCards.add("Jester");
        deckOfCharacterCards.add("Baker");
        deckOfCharacterCards.add("Messenger");
    }


    public ArrayList<CharacterCard> drawCharacterCard() {
        Random random = new Random();
        ArrayList<CharacterCard> deckForAMatch = new ArrayList<>();
        FactoryCharacterCard tmp = new FactoryCharacterCard(match);
        for (int i = 0; i < 3; i++) {
            int toChoose = random.nextInt(deckOfCharacterCards.size());
            deckForAMatch.add(tmp.createACharacterCard(deckOfCharacterCards.get(toChoose),match.getGame()));
        }
        return deckForAMatch;
    }

}