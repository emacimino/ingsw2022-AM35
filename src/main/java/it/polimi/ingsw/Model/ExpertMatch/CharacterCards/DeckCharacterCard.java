package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.FactoryCharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This Class represents the deck containing all Character cards
 */
public class DeckCharacterCard{
    private final ArrayList<String> deckOfCharacterCards = new ArrayList<>();

    /**
     * Constructor of the class
     */
    public DeckCharacterCard() {
        setDeckOfCharacterCards();
    }

    /**
     * This method sets the deck of cards correctly
     */
    private void setDeckOfCharacterCards() {
        deckOfCharacterCards.add("Friar"); //ok
        deckOfCharacterCards.add("Knight"); //ok
        deckOfCharacterCards.add("Chef"); //ok
        deckOfCharacterCards.add("Archer"); //ok
        deckOfCharacterCards.add("Princess"); //ok
        deckOfCharacterCards.add("Jester"); //ok
        deckOfCharacterCards.add("Baker"); //ok
        deckOfCharacterCards.add("Messenger"); //ok
        deckOfCharacterCards.add("Minstrel"); //ok
        deckOfCharacterCards.add("Magician"); //ok
        deckOfCharacterCards.add("Banker"); //ok
        deckOfCharacterCards.add("Herbalist");
    }

    /**
     * This method is used to draw an Array of cards at the start of the match
     * @param basicMatch current match
     * @return the deck for the match
     */
    public Map<String, CharacterCard> drawCharacterCard(BasicMatch basicMatch) {
        Random random = new Random();
        Map<String, CharacterCard> deckForAMatch = new HashMap<>();
        FactoryCharacterCard factoryCharacterCard = new FactoryCharacterCard();
        for (int i = 0; i < 3; i++) {
            int toChoose = random.nextInt(deckOfCharacterCards.size());
            CharacterCard drawnCharacterCard = factoryCharacterCard.createACharacterCard(basicMatch, deckOfCharacterCards.get(toChoose));
            deckForAMatch.put(drawnCharacterCard.getName(), drawnCharacterCard);
            deckOfCharacterCards.remove(toChoose);
        }
        if(!deckForAMatch.containsKey("Friar")) {
            deckForAMatch.remove(deckForAMatch.keySet().stream().toList().get(0));
            deckForAMatch.put("Friar", factoryCharacterCard.createACharacterCard(basicMatch, "Friar"));
        }
        return deckForAMatch;
    }

}