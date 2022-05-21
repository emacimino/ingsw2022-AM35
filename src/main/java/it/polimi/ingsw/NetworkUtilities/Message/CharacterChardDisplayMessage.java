package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;

import java.util.List;

public class CharacterChardDisplayMessage extends Message{
    private static final long serialVersionUID = 4902054771178027494L;
    private final List<CharacterCard> characterCards;

    public CharacterChardDisplayMessage(List<CharacterCard> characterCards) {
        this.characterCards = characterCards;
        setType(TypeMessage.SHOW_CHARACTER_CARD);
    }

    public List<CharacterCard> getCharacterCards() {
        return characterCards;
    }
}
