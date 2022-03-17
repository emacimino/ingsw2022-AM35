package it.polimi.ingsw;

import java.util.Collection;

public class DeckHelper {
    private Collection <Card> PlayableCards = new Collection<Card>();
    private Collection <Card> UsedCards = new Collection<Card>();

    public DeckHelper(Collection<Card> playableCards, Collection<Card> usedCards) {
        PlayableCards = playableCards;
        UsedCards = usedCards;
    }

    public Collection<Card> getPlayableCards() {
        return PlayableCards;
    }

    public Collection<Card> getUsedCards() {
        return UsedCards;
    }

    public void setPlayableCards(Collection<Card> playableCards) {
        PlayableCards = playableCards;
    }

    public void setUsedCards(Collection<Card> usedCards) {
        UsedCards = usedCards;
    }
}
