package it.polimi.ingsw.ModelTest.SchoolsLandsTest.WizardTest;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.AssistantsDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AssistantsDeckTest {

    @Test
    protected void getPlayableAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertFalse(assistantsDeckTest.getPlayableAssistants().isEmpty());
    }

    @Test
    protected void getUsedAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertTrue(assistantsDeckTest.getUsedAssistants().isEmpty());
    }

    @Test
    void usedAssistantCardTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        assistantsDeckTest.usedAssistantCard(AssistantsCards.CardFour);
        Assertions.assertFalse(assistantsDeckTest.getUsedAssistants().isEmpty());
    }
}