package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.DeckCharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * this class extends the MatchDecorator to implements the ExpertMode
 */
public class ExpertMatch extends MatchDecorator {
    private final DeckCharacterCard deckCharacterCard;
    private ArrayList<CharacterCard> charactersForThisGame = new ArrayList<>();
    private Color CHEF_EFFECT;
    private boolean ARCHER_EFFECT;
    private Wizard BAKER_EFFECT;
    private Student PRINCESS_EFFETC;
    /**
     * Constructor of ExpertMatch
     *
     * @param basicMatch is the match that has to be modified to play in ExpertMode
     */
    public ExpertMatch(BasicMatch basicMatch) {
        super(basicMatch); //match della classe MatchDecorator
        deckCharacterCard = new DeckCharacterCard();
        CHEF_EFFECT = null;
        ARCHER_EFFECT = false;
        BAKER_EFFECT = null;
    }

    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
        basicMatch.setGame(players);
        setFirstCoin();
        charactersForThisGame = deckCharacterCard.drawCharacterCard(getGame());
    }

    /**
     * The override add a check to the number of student that are placed on the board when a student is moved
     *
     * @param player  is the player which moves the student
     * @param student is the student
     */
    @Override
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame {
        basicMatch.moveStudentOnBoard(player, student);
        if (basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 3 ||
                basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 6 ||
                basicMatch.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 9) {
                basicMatch.getGame().getWizardFromPlayer(player).addACoin();
        }
    }

    /**
     * Add a coin to every wizard when we set the Match to Expert Mode
     */
    private void setFirstCoin() {
        for (Wizard wizard : basicMatch.getGame().getWizards())
            wizard.addACoin();
    }

    @Override
    public ArrayList<CharacterCard> getCharactersForThisGame() {
        return charactersForThisGame;
    }


    @Override
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        basicMatch.getGame().placeMotherNature(player, archipelago);
        try {
            buildTower(player, archipelago);
            basicMatch.lookUpArchipelago(archipelago);
        } catch (ExceptionGame e) {
            System.out.println(e);
        } finally {
            basicMatch.checkVictory();
        }
        if (player.equals(basicMatch.getActionPhaseOrderOfPlayers().get(basicMatch.getActionPhaseOrderOfPlayers().size() - 1))) {
            basicMatch.resetRound();
        }
        if(getBakerEffect() != null)
            setBakerEffect(null);
        if(getChefEffect()!= null)
            setChefEffect(null);
        if(getArcherEffect())
            setArcherEffect(false);
    }

    protected void buildTower(Player player, Archipelago archipelago) throws ExceptionGame {
        boolean isMostInfluence = true;
        for(Player p : getRivals(player)){
            if(getWizardInfluenceInArchipelago(p, archipelago) >= getWizardInfluenceInArchipelago(player, archipelago)) { //influenza del player in negativo
                System.out.println("influence 1 : "+ getWizardInfluenceInArchipelago(p, archipelago) + " influence 2 : " + getWizardInfluenceInArchipelago(player, archipelago));
                isMostInfluence = false;
            }
        }
        System.out.println("most influence is " + isMostInfluence);
        if(isMostInfluence) {
            System.out.println("Captain of player " + player + "is " + basicMatch.getCaptainTeamOfPlayer(player));
            System.out.println("building tower of " + basicMatch.getCaptainTeamOfPlayer(player));
            basicMatch.getGame().buildTower( getGame().getWizardFromPlayer(basicMatch.getCaptainTeamOfPlayer(player)), archipelago);
        }


    }

    @Override
    public int getWizardInfluenceInArchipelago(Player p, Archipelago archipelago) throws ExceptionGame{
        Wizard wizard = getGame().getWizardFromPlayer(basicMatch.getCaptainTeamOfPlayer(p));
        int CardEffectWizardInfluence = basicMatch.getWizardInfluenceInArchipelago(p, archipelago);
        if(CHEF_EFFECT != null){
            int colorInfluence = 0;
            for(Island island : archipelago.getIsle()){
                colorInfluence = island.getStudentFilteredByColor(CHEF_EFFECT).size();
            }
            if(basicMatch.playerControlProfessor(p, CHEF_EFFECT)) //problema x 4
                CardEffectWizardInfluence = CardEffectWizardInfluence - colorInfluence;
        }
        if(ARCHER_EFFECT){
            int towerInfluence = archipelago.calculateInfluenceTowers(wizard); //va bene perchè player p è il captain sempre
            CardEffectWizardInfluence = CardEffectWizardInfluence - towerInfluence;
        }
        if(BAKER_EFFECT != null){   //va bene perchè non mi interessa i giocatori singoli
            if(BAKER_EFFECT.equals(wizard)){
                CardEffectWizardInfluence = archipelago.getStudentFromArchipelago().size() + archipelago.calculateInfluenceTowers(wizard);
                System.out.println( " influenza degli studenti : "+ archipelago.getStudentFromArchipelago().size() + ", influenza delle torri di " + wizard + ": " + archipelago.calculateInfluenceInArchipelago(wizard));
            }else{
                CardEffectWizardInfluence = archipelago.calculateInfluenceTowers(wizard);
            }
        }

        return CardEffectWizardInfluence;
    }

    public Color getChefEffect() {
        return CHEF_EFFECT;
    }
    public void setChefEffect(Color CHEF_EFFECT) {
        this.CHEF_EFFECT = CHEF_EFFECT;
    }
    public void setArcherEffect(boolean archerEffect){
        ARCHER_EFFECT = archerEffect;
    }
    public boolean getArcherEffect(){
        return ARCHER_EFFECT;
    }
    public void setBakerEffect(Wizard bakerEffect){BAKER_EFFECT = bakerEffect;}
    public Wizard getBakerEffect(){return BAKER_EFFECT;}
}


