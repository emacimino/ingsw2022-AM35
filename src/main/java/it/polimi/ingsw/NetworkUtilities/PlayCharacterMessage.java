package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;
import java.util.List;

/**
 * Class used to send a charachter card to play
 */
public class PlayCharacterMessage extends Message{
    @Serial
    private static final long serialVersionUID = -7642905185840013305L;
    private final String nameCharacterCard;
    private final int indexOfArchipelago;
    private final List<Integer> toTradeFromEntrance;
    private final List<Color> color;
    private final List<Integer> toTradeFromCard;

    /**
     * Constructor of the class
     * @param characterCard the name of the card
     * @param indexOfArchipelago index of target archipelago
     * @param toTradeFromEntrance students to trade from the entrance in the board
     * @param toTradeFromCard students to trade from the card
     * @param color color selected for the effect
     */
    public PlayCharacterMessage(String characterCard, int indexOfArchipelago, List<Integer> toTradeFromEntrance, List<Integer> toTradeFromCard, List<Color> color) {
        this.nameCharacterCard = characterCard;
        this.indexOfArchipelago = indexOfArchipelago;
        this.toTradeFromEntrance = toTradeFromEntrance;
        this.toTradeFromCard = toTradeFromCard;
        this.color = color;
        super.setType(TypeMessage.PLAY_CHARACTER_CARD);
    }

    /**
     * Method that returns the name of the card
     * @return a string indicating the name of the card
     */
    public String getNameCharacterCard() {
        return nameCharacterCard;
    }

    /**
     * Method that returns the index of the target archipelago
     * @return an integer (index of the archipelago)
     */
    public int getIndexOfArchipelago() {
        return indexOfArchipelago;
    }

    /**
     * Method that returns a list of student to trade from entrance
     * @return a list of integers (indexes of the students)
     */
    public List<Integer> getToTradeFromEntrance() {
        return toTradeFromEntrance;
    }

    /**
     * Method that returns a list of students to trade from the card
     * @return a list of integers (indexes of the students)
     */
    public List<Integer> getToTradeFromCard() {
        return toTradeFromCard;
    }

    /**
     * Method that returns the color selected
     * @return a list of colors
     */
    public List<Color> getColors() {
        return color;
    }
}
