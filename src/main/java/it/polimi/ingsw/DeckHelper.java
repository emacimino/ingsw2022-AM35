package it.polimi.ingsw;

import java.util.*;
import java.util.List;

public class DeckHelper {
    private Collection<HelperCard> PlayableCards = new ArrayList<HelperCard>();
    private Collection <HelperCard> UsedCards = new ArrayList<HelperCard>();

    public DeckHelper(Collection<HelperCard> playableCards, Collection<HelperCard> usedCards) {
        PlayableCards = playableCards;
        UsedCards = usedCards;
    }

    public Collection<HelperCard> getPlayableCards() {
        return PlayableCards;
    }

    public Collection<HelperCard> getUsedCards() {
        return UsedCards;
    }

    public void setPlayableCards(Collection<HelperCard> playableCards) {
        PlayableCards = playableCards;
    }

    public void setUsedCards(Collection<HelperCard> usedCards) {
        UsedCards = usedCards;
    }
}
