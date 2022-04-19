package it.polimi.ingsw.Model.ExpertMatch;


import java.util.Random;

public class ExpertMatch {
    private DeckCharacterCard deckCharacterCard;
   private CharacterCard CharacterOne = deckCharacterCard.drawCharacterCard();
   private CharacterCard CharacterTwo = deckCharacterCard.drawCharacterCard();
   private CharacterCard CharacterThree = deckCharacterCard.drawCharacterCard();



    public void expertMatch(Match match){

    }

    public CharacterCard drawCharacterCard(){
        Random random = new Random();
        private String[] draw = {"Chara"}
        CharacterCard characterCard;
                
        return characterCard.remove(random.nextInt(studentsInBag.size()));
    }
    /*
+expertMatch( Match): void
+drawCharacterCard(): void
+setFirstCoin(): void
            (@override)+moveStudentOnBoard(Player, Student): void

+useCoins(CharacterCard): void

+playCharacterCard(Player, CharacterCard): void
*/
}
