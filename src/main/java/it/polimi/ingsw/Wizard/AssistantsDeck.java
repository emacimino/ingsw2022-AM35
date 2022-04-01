package it.polimi.ingsw.Wizard;

import java.util.ArrayList;

/**
 *Instantiate a deck of Assistants for every Wizard
 */
public class AssistantsDeck {
   private final ArrayList<AssistantsCards> playableAssistants;
   private final ArrayList<AssistantsCards> usedAssistants;

    /**
     *Instantiate a deck of Assistants
     */
    public AssistantsDeck() {
        this.playableAssistants = new ArrayList<>();
        this.usedAssistants = new ArrayList<>();

        playableAssistants.add(AssistantsCards.CardOne);
        playableAssistants.add(AssistantsCards.CardTwo);
        playableAssistants.add(AssistantsCards.CardThree);
        playableAssistants.add(AssistantsCards.CardFour);
        playableAssistants.add(AssistantsCards.CardFive);
        playableAssistants.add(AssistantsCards.CardSix);
        playableAssistants.add(AssistantsCards.CardSeven);
        playableAssistants.add(AssistantsCards.CardEight);
        playableAssistants.add(AssistantsCards.CardNine);
        playableAssistants.add(AssistantsCards.CardTen);
    }


    /**
     *
     * @return Assistants that could be played
     */
    public ArrayList<AssistantsCards> getPlayableAssistants() {
        return playableAssistants;
    }

    /**
     *
     * @return Assistants that could not be played
     */
    public ArrayList<AssistantsCards> getUsedAssistants() {
        return usedAssistants;
    }

    /**
     * When a card is used it is removed from the playable assistant and added to the usedAssistant
     * @param assistantCard is an instance of AssistantsCard
     */
    public void usedAssistantCard(AssistantsCards assistantCard){
        playableAssistants.remove(assistantCard);
        usedAssistants.add(assistantCard);
    }
}
