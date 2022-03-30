package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssistantsDeckTest {

    @Test
    void getPlayableAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertEquals(assistantsDeckTest.getPlayableAssistants(), assistantsDeckTest.playableAssistants);
    }

    @Test
    void getUsedAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertEquals(assistantsDeckTest.getUsedAssistants(), assistantsDeckTest.usedAssistants);
    }

    @Test
    void usedAssistantCardTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        assistantsDeckTest.usedAssistantCard(AssistantsCards.CardFour);
        Assertions.assertEquals(assistantsDeckTest.getPlayableAssistants(), assistantsDeckTest.playableAssistants);
        Assertions.assertEquals(assistantsDeckTest.getUsedAssistants(), assistantsDeckTest.usedAssistants);
    }
}