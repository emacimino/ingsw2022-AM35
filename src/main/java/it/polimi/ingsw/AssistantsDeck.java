package it.polimi.ingsw;

import java.util.ArrayList;

/**
 *Instatiate a deck of Assistants for every Wizard
 */
public class AssistantsDeck {
    ArrayList<AssistantsCards> playableAssistants;
    ArrayList<AssistantsCards> usedAssistants;

    /**
     *
     */
    public AssistantsDeck() {
        this.playableAssistants = playableAssistants;
        this.usedAssistants = usedAssistants;

        for(AssistantsCards assistantsCards: AssistantsCards.values() ){
            playableAssistants.add(assistantsCards);
        }
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
     * When a card is used it is removed from playable assistant and added to usedAssistant
     * @param assistantCard
     */
    public void usedAssistantCard(AssistantsCards assistantCard){
        playableAssistants.remove(assistantCard);
        usedAssistants.add(assistantCard);
    }
}
