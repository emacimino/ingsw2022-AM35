package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.io.Serial;
import java.util.List;
/**
Used to ask the usage of an assistantCard
 */
public class AskAssistantCardMessage extends Message {

    @Serial
    private static final long serialVersionUID = 4902054771178027494L;
    private final List<AssistantsCards> assistantsCards;

    /**
     * constructor of the class
     * @param assistantsCards assistants in game
     */
    public AskAssistantCardMessage(List<AssistantsCards> assistantsCards) {
        this.assistantsCards = assistantsCards;
        setType(TypeMessage.ASK_ASSISTANT_CARD);
    }

    /**
     * Getter for assistant Card
     * @return assistant Card list
     */
    public List<AssistantsCards> getAssistantsCards() {
        return assistantsCards;
    }
}
