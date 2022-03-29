package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class AssistantsDeckTest {

    public ArrayList<AssistantsCards> ()    {
        ArrayList<AssistantsCards> ar = new ArrayList<AssistantsCards>();
        ar.add();
        ar.add();
        ar.add();
        return();
    }
    @Test
    void getPlayableAssistants(AssistantsDeck assistantsDeck) {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        for(AssistantsCards assistantDeckTest : assistantsDeck.playableAssistants);
        Assertions.assertArrayEquals(assistantsDeckTest.playableAssistants, assistantsDeck.playableAssistants);
    }

    @Test
    void getUsedAssistants() {
    }

    @Test
    void usedAssistantCard() {
    }
}