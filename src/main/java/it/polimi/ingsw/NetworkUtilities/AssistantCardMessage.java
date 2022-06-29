package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.io.Serial;

/**
 * Class used to send an assistant card
 */
public class AssistantCardMessage extends Message{
    @Serial
    private static final long serialVersionUID = 7250516904545301604L;
    private final AssistantsCards assistantsCard;

    /**
     * Constructor of the class
     * @param assistantCard an assistant card
     */
    public AssistantCardMessage( AssistantsCards assistantCard) {
        this.assistantsCard = assistantCard;
        setType(TypeMessage.ASSISTANT_CARD);
    }

    /**
     * Method that returns the assistant card contained in the message
     * @return an assistant card
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
