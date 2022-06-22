package it.polimi.ingsw.ViewTest;

import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class showAssistantsCardTest {
    List<AssistantsCards> assistantsCards = new ArrayList<>();

    @Test
    void showTest(){
        assistantsCards.add(AssistantsCards.CardFive);
        assistantsCards.add(AssistantsCards.CardNine);
        assistantsCards.add(AssistantsCards.CardOne);

      //  SceneController.showAssistantsCardOption(assistantsCards);

    }
}
