package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.io.Serial;

/**
 * Communicate the card used
 */
public class AssistantCardMessage extends Message{
    @Serial
    private static final long serialVersionUID = 7250516904545301604L;
    private final AssistantsCards assistantsCard;

    /**
     * Constructor of the class
     * @param assistantCard assistant card used
     */
    public AssistantCardMessage( AssistantsCards assistantCard) {
        this.assistantsCard = assistantCard;
        setType(TypeMessage.ASSISTANT_CARD);
    }

    /**
     * Getter of the assistant card sent
     * @return assistant card used
     */
    public AssistantsCards getAssistantsCard() {
        return assistantsCard;
    }

    /**
     * Override of toString
     * @return the values of assistant card
     */
    @Override
    public String toString() {
        return "AssistantCardMessage{" +
                "assistantsCard=" + assistantsCard +
                '}';
    }
}
