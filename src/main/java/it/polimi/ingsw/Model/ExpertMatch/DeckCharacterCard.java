package it.polimi.ingsw.Model.ExpertMatch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Math.random;

public class DeckCharacterCard {
    ArrayList<String> deckOfCharacterCards = new ArrayList<>();

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

    public ArrayList<String> getDeckOfCharacterCards() {
        return deckOfCharacterCards;
    }

    public Set<String> getDeckOfCharacterCardsForThisMatch() {
        Set<String> deckOfCharacterCardForThisMatch = new HashSet<>();
        String temp;
        Random random = new Random();
        //temp = deckOfCharacterCards.(random.nextInt(studentsInBag.size()))
        //deckOfCharachterCardForThisMatch.add(deckOfCharacterCards.(random.nextInt(studentsInBag.size()))
        deckOfCharacterCardForThisMatch.add(deckOfCharacterCards.stream().toString(random.nextInt(deckOfCharacterCards.size())))

        return deckOfCharacterCardForThisMatch;
    }
}