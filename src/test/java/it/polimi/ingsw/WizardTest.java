package it.polimi.ingsw;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WizardTest {

    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    @DisplayName("playAssistantCardThrowsException_Test (Method setRoundAssistantCard was tested in local since is a private method)")
    void playAssistantCardThrowsException_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test");
        if(!wizard.playableAssistantsCard(cardPlayed, playedByOpponent)){
            Assertions.assertThrows(ExceptionGame.class, ()-> wizard.playAssistantsCard(cardPlayed, playedByOpponent));
        }
    }


    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void playableAssistantCard_Test(AssistantsCards cardPlayed) {
        Wizard wizard = new Wizard("player_test");
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        if(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent) && wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent))
            Assertions.assertFalse(wizard.playableAssistantsCard(cardPlayed, playedByOpponent));
        else
            Assertions.assertTrue(wizard.playableAssistantsCard(cardPlayed,playedByOpponent));

        wizard.getAssistantsDeck().playableAssistants.removeIf(a -> !a.equals(cardPlayed));
        if(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed,playedByOpponent) && !wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent))
            Assertions.assertTrue(wizard.playableAssistantsCard(cardPlayed,playedByOpponent));
    }

    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void checkIfAssistantCardAlreadyPlayed_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent= combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test");
        if(playedByOpponent.contains(cardPlayed)) {
            Assertions.assertTrue(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent));
        }else
            Assertions.assertFalse(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed,playedByOpponent));

        Collection<AssistantsCards> playedByOpponent2 = new ArrayList<>();
        Assertions.assertFalse(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent2));

    }


    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void checkIfThereIsAlternativeAssistantCard_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent= combinationOfThreeAssistantsCards();
       Wizard wizard = new Wizard("player_test"); //It is sure that wizard.assistantsDeck has an alternative
       boolean isThereAlternative = wizard.checkIfThereIsAlternativeAssistantsCard( playedByOpponent);
       Assertions.assertTrue(isThereAlternative);   //Verified that there is an alternative

        wizard.getAssistantsDeck().playableAssistants.removeIf(a -> !a.equals(cardPlayed));

        if(playedByOpponent.contains(cardPlayed)){
            boolean isThereAlternative_false = wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent);
            Assertions.assertFalse(isThereAlternative_false);
        }



    }

    @Disabled("Disabled until Board is created")
    @Test
    void placeStudentOnArchipelago() {
    }

    @Disabled("Disabled until Board is created")
    @Test
    void placeStudentOnTable() {

    }

    @Disabled("Disabled until Board is created")
    @Test
    void checkIfStudentIsMovable(Student s) {
    }

    public Collection<AssistantsCards> combinationOfThreeAssistantsCards(){
        Collection<AssistantsCards> combination = new ArrayList<>();
        AssistantsDeck assistantsDeck = new AssistantsDeck();
        Random r = new Random();

        for(int i = 0; i<3; i++){
            AssistantsCards a = assistantsDeck.playableAssistants.remove(r.nextInt(assistantsDeck.playableAssistants.size()));
            combination.add(a);
            System.out.println("Assistant Card: value = " + a.getValue() + "; step = " + a.getStep() );
        }
        System.out.println();

        return combination;
    }
}