package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.List;

public class AskAssistantCardMessage extends Message {

    private static final long serialVersionUID = 4902054771178027494L;
    private final List<AssistantsCards> assistantsCards;

    public AskAssistantCardMessage(List<AssistantsCards> assistantsCards) {
        this.assistantsCards = assistantsCards;
        setType(TypeMessage.ASK_ASSISTANT_CARD);
    }

    public List<AssistantsCards> getAssistantsCards() {
        return assistantsCards;
    }
}
