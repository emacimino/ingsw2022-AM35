package it.polimi.ingsw.NetworkUtilities.Message;

public class AssistantCard extends Message{
    private static final long serialVersionUID = 7250516904545301604L;

    public AssistantCard(String assistantCard, GameStateMessage type) {
        super(assistantCard.toString(), GameStateMessage.ASSISTANTCARD);
    }

}
