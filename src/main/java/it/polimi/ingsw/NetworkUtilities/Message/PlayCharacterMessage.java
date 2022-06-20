package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Color;

import java.io.Serial;
import java.util.List;

public class PlayCharacterMessage extends Message{
    @Serial
    private static final long serialVersionUID = -7642905185840013305L;
    private final CharacterCard characterCard;
    private final Integer indexOfArchipelago;
    private final List<Integer> toTradeFromEntrance;
    private final List<Color> toTradeFromTables;
    private final List<Integer> toTradeFromCard;
    private final Color affectedColor;

    public PlayCharacterMessage(CharacterCard characterCard, int indexOfArchipelago, List<Integer> toTradeFromEntrance, List<Integer> toTradeFromCard, List<Color> toTradeFromTables, Color affectedColor) {
        this.characterCard = characterCard;
        this.indexOfArchipelago = indexOfArchipelago;
        this.toTradeFromEntrance = toTradeFromEntrance;
        this.toTradeFromCard = toTradeFromCard;
        this.toTradeFromTables = toTradeFromTables;
        this.affectedColor = affectedColor;

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

    public List<Color> getToTradeFromTables() {
        return toTradeFromTables;
    }

    public Color getAffectedColor() {
        return affectedColor;
    }
}
