package it.polimi.ingsw.ModelTest.WizardTest;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.AssistantsDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class used to test the assistant cards' deck
 */
class AssistantsDeckTest {
    /**
     * Test that fails if a deck is empty
     */
    @Test
    protected void getPlayableAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertFalse(assistantsDeckTest.getPlayableAssistants().isEmpty());
    }

    /**
     * Test that fails if the used assistant cards' deck is not empty
     */
    @Test
    protected void getUsedAssistantsTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        Assertions.assertTrue(assistantsDeckTest.getUsedAssistants().isEmpty());
    }

    /**
     * Test that fails if the used assistant cards' deck is empty
     */
    @Test
    void usedAssistantCardTest() {
        AssistantsDeck assistantsDeckTest = new AssistantsDeck();
        assistantsDeckTest.usedAssistantCard(AssistantsCards.CardFour);
        Assertions.assertFalse(assistantsDeckTest.getUsedAssistants().isEmpty());
    }
}