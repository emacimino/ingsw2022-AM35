package it.polimi.ingsw;

public class Wizard {
    private final Board board;
    private final DeckHelper deckhelper;
    private String nameWizard;
    private String namePlayer;
    private int coins;


    public Wizard(Board board, DeckHelper deckhelper, String nameWizard, String namePlayer, int coins) {
        this.board = board;
        this.deckhelper = deckhelper;
        this.nameWizard = nameWizard;
        this.namePlayer = namePlayer;
        this.coins = coins;
    }

    public void setNameWizard(String nameWizard) {
        this.nameWizard = nameWizard;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Board getBoard() {
        return board;
    }

    public DeckHelper getDeckhelper() {
        return deckhelper;
    }

    public String getNameWizard() {
        return nameWizard;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public int getCoins() {
        return coins;
    }

    public void Planification(){};

    public void Action(){};

    public Card SelectHelperCard(){};

    public Student SelectStudentFromEntrance(){};

    public void MoveStudent(){};

    public void SetStudentInIsland(Island){};

    public void PlayCoins(){};

}
