package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.io.Serial;
import java.util.List;

/**
 * Class used to send a list of assistant cards
 */
public class AskAssistantCardMessage extends Message {

    @Serial
    private static final long serialVersionUID = 4902054771178027494L;
    private final List<AssistantsCards> assistantsCards;

    /**
     * Constructor of the class
     * @param assistantsCards a list of Assistant cards
     */
    public AskAssistantCardMessage(List<AssistantsCards> assistantsCards) {
        this.assistantsCards = assistantsCards;
        setType(TypeMessage.ASK_ASSISTANT_CARD);
    }

    /**
     * A method that returns a list of cards  contained in the message
     * @return List of cards
     */
    public List<AssistantsCards> getAssistantsCards() {
        return assistantsCards;
    }
}
