package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.util.List;

public class PlayCharacterMessage extends Message{
    @Serial
    private static final long serialVersionUID = -7642905185840013305L;
    private final String nameCharacterCard;
    private final int indexOfArchipelago;
    private final List<Integer> toTradeFromEntrance;
    private final List<Color> color;
    private final List<Integer> toTradeFromCard;

    public PlayCharacterMessage(String characterCard, int indexOfArchipelago, List<Integer> toTradeFromEntrance, List<Integer> toTradeFromCard, List<Color> color) {
        this.nameCharacterCard = characterCard;
        this.indexOfArchipelago = indexOfArchipelago;
        this.toTradeFromEntrance = toTradeFromEntrance;
        this.toTradeFromCard = toTradeFromCard;
        this.color = color;
        super.setType(TypeMessage.PLAY_CHARACTER_CARD);
    }


    public String getNameCharacterCard() {
        return nameCharacterCard;
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


    public List<Color> getColors() {
        return color;
    }
}
