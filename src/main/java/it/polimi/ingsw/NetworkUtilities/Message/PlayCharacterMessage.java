package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.util.List;

public class PlayCharacterMessage extends Message{
    @Serial
    private static final long serialVersionUID = -7642905185840013305L;
    private final CharacterCard characterCard;
    private int indexOfArchipelago;
    private final List<Integer> toTradeFromEntrance;
    private final List<Integer> toTradeFromTables;
    private final List<Integer> toTradeFromCard;

    public PlayCharacterMessage(CharacterCard characterCard, int indexOfArchipelago, List<Integer> toTradeFromEntrance, List<Integer> toTradeFromCard, List<Integer> toTradeFromTables, int numOfStep) {
        this.characterCard = characterCard;
        this.indexOfArchipelago = indexOfArchipelago;
        this.toTradeFromEntrance = toTradeFromEntrance;
        this.toTradeFromCard = toTradeFromCard;
        this.toTradeFromTables = toTradeFromTables;
        super.setType(TypeMessage.PLAY_CHARACTER_CARD);
    }


    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public int getIndexOfArchipelago() {
        return indexOfArchipelago;
    }

    public List<Integer> getToTradeFromEntrance() {
        return toTradeFromEntrance;
    }

    public List<Integer> getToTradeFromCard() {
        return toTradeFromCard;
    }

    public List<Integer> getToTradeFromTables() {
        return toTradeFromTables;
    }
}
