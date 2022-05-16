package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.List;

public class AssistantCardListMessage extends Message {

    private static final long serialVersionUID = 4902054771178027494L;
    private final List<AssistantsCards> assistantsCards;

    public AssistantCardListMessage(List<AssistantsCards> assistantsCards) {
        this.assistantsCards = assistantsCards;
    }
}
