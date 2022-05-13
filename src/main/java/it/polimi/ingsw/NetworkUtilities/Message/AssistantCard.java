package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

public class AssistantCard extends Message{
    private static final long serialVersionUID = 7250516904545301604L;

    public AssistantCard(String nickname, AssistantsCards assistantCard) {
        super(nickname, assistantCard, GameStateMessage.ASSISTANTCARD);
    }

}
