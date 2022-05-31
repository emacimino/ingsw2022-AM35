package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.HashMap;
import java.util.Map;

public class CardMap {
    public static Map<String, String> characterCardImageMap = new HashMap<>();
    static {
        characterCardImageMap.put("Princess", "/Graphics/Personaggi/CarteTOT_front10.jpg");
        characterCardImageMap.put("Baker", "/Graphics/Personaggi/CarteTOT_front12.jpg");
        characterCardImageMap.put("Banker", "/Graphics/Personaggi/CarteTOT_front11.jpg");
        characterCardImageMap.put("Minstrel", "/Graphics/Personaggi/CarteTOT_front9.jpg");
        characterCardImageMap.put("Chef", "/Graphics/Personaggi/CarteTOT_front8.jpg");
        characterCardImageMap.put("Knight", "/Graphics/Personaggi/CarteTOT_front7.jpg");
        characterCardImageMap.put("Jester", "/Graphics/Personaggi/CarteTOT_front6.jpg");
        characterCardImageMap.put("Archer", "/Graphics/Personaggi/CarteTOT_front5.jpg");
        characterCardImageMap.put("Herbalist", "/Graphics/Personaggi/CarteTOT_front4.jpg");
        characterCardImageMap.put("Magician", "/Graphics/Personaggi/CarteTOT_front3.jpg");
        characterCardImageMap.put("Messenger", "/Graphics/Personaggi/CarteTOT_front2.jpg");
        characterCardImageMap.put("Friar", "/Graphics/Personaggi/CarteTOT_front.jpg");
    }

    public static Map<AssistantsCards, String> assistantsCardsImageMap = new HashMap<>();
    static {
        assistantsCardsImageMap.put(AssistantsCards.CardOne, "/Graphics/Assistents/2x/Assistente(1).png");
        assistantsCardsImageMap.put(AssistantsCards.CardTwo, "/Graphics/Assistents/2x/Assistente(2).png");
        assistantsCardsImageMap.put(AssistantsCards.CardThree, "/Graphics/Assistents/2x/Assistente(3).png");
        assistantsCardsImageMap.put(AssistantsCards.CardFour, "/Graphics/Assistents/2x/Assistente(4).png");
        assistantsCardsImageMap.put(AssistantsCards.CardFive, "/Graphics/Assistents/2x/Assistente(5).png");
        assistantsCardsImageMap.put(AssistantsCards.CardSix, "/Graphics/Assistents/2x/Assistente(6).png");
        assistantsCardsImageMap.put(AssistantsCards.CardSeven, "/Graphics/Assistents/2x/Assistente(7).png");
        assistantsCardsImageMap.put(AssistantsCards.CardEight, "/Graphics/Assistents/2x/Assistente(8).png");
        assistantsCardsImageMap.put(AssistantsCards.CardNine, "/Graphics/Assistents/2x/Assistente(9).png");
        assistantsCardsImageMap.put(AssistantsCards.CardTen, "/Graphics/Assistents/2x/Assistente(10).png");
    }




}
