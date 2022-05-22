package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.*;
import it.polimi.ingsw.Model.SchoolsMembers.*;
import it.polimi.ingsw.Model.Wizard.*;

import java.util.*;

/**
 * Game class represents the state of the match, it contains the implements the objects of the game Eriantys
 */
public class Game implements Cloneable{
    private final List<Wizard> wizards = new ArrayList<>();
    private final Collection<AssistantsCards> assistantsCardsPlayedInRound = new ArrayList<>();
    private final List<Archipelago> archipelagos = new ArrayList<>();
    private final StudentBag studentBag = new StudentBag();
    private final List<Professor> professors = new ArrayList<>();
    private final Collection<Cloud> clouds = new ArrayList<>();
    private final MotherNature motherNature = new MotherNature();
    private final int limitOfStudentInEntrance;
    private final int numOfStudentMovable;


    /**
     * Constructor of Game class
     * @param limitOfStudentInEntrance is the number of Students allowed in the entrance of the board of each wizard
     * @param numOfStudentMovable is the number of students movable by the player in one round
     */
    public Game(int limitOfStudentInEntrance, int numOfStudentMovable){
        this.limitOfStudentInEntrance = limitOfStudentInEntrance;
        this.numOfStudentMovable = numOfStudentMovable;
    }

    /**
     * This method sets the relation between the players and the wizards of the game
     * @param player is the collection of player
     */
    public void setWizards(List<Player> player){
        for(Player p: player){
            wizards.add(new Wizard(p.getUsername(), limitOfStudentInEntrance, numOfStudentMovable));
        }
    }

    /**
     * This method sets the Towers of each wizard on his corresponding board
     * @param numOfTowers is the number of towers which have to be settled on the board
     */
    public void setTowers(int numOfTowers){
        for(Wizard w : wizards){
            for(int i = 0; i<numOfTowers; i++)
                 w.getBoard().getTowersInBoard().add(new Tower(w));
        }
    }

    /**
     * This method sets the number of clouds presents in the game and how many students are on top of each
     * @param numberOfClouds is the number of clouds in the game
     * @param numberOfStudentsOnCloud is the number of students on each cloud
     */
    public void setClouds(int numberOfClouds, int numberOfStudentsOnCloud) throws ExceptionGame {
        for( int i=0; i<numberOfClouds; i++){
            clouds.add(new Cloud(numberOfStudentsOnCloud));
        }
        setRandomStudentsOnCloud();
    }

    /**
     * This method sets the professors of the game, one for each value of Color
     */
    public void setProfessors(){
        for(Color c : Color.values()){
            professors.add(new Professor(c));
        }
    }

    /**
     * This method initialize the archipelagos of the game, it puts 2 students on each archipelago
     * except for the one which contains mother nature or is the opposite of the archipelago who contains
     * mother nature, the method throws an exception if there is an error with the set of mother nature
     * @throws ExceptionGame is thrown if there is an error with the set of mother nature
     */
    public void setArchipelagos() throws ExceptionGame{
        for(int i = 0; i<12; i++)
            archipelagos.add(new Archipelago());
        Random random = new Random();
        int position = random.nextInt(12);
        motherNature.setPosition(position);
        archipelagos.get(position).setMotherNaturePresence(true);

        for(Archipelago a : archipelagos) {
            if (archipelagos.indexOf(a) != position && archipelagos.indexOf(a) != (position + 6) % 12) {
                Student student1 = studentBag.drawStudent();
                Student student2 = studentBag.drawStudent();
                a.addStudentInArchipelago(student1);
                a.addStudentInArchipelago(student2);
            }
        }

    }

    /**
     * This method returns the wizard who is associated to the player passed to Game, if there is no wizard
     * associated to the player the method throws an exception
     * @param player is the player
     * @return the wizard associated to the player
     * @throws ExceptionGame is thrown where there is no association between the player and the wizards in the game
     */
    public Wizard getWizardFromPlayer(Player player) throws ExceptionGame {
        for (Wizard w : wizards){
            if(player.getUsername().equals(w.getUsername()))
                return w;
        }
        throw new ExceptionGame("Player is not in this Game");
    }

    /**
     * This method returns the influence of the player in the archipelago passed as parameters of the method
     * if the player does not have a corresponding wizard in the game, the method throws an exception
     * @param wizard is the wizard
     * @param archipelago is the archipelago
     * @return the influence of the player in the archipelago
     */
    public int getWizardInfluenceInArchipelago(Wizard wizard, Archipelago archipelago){

        return archipelago.calculateInfluenceInArchipelago(wizard);
    }

    /**
     * This method returns the wizard who has most students of the color passed as parameter, if there are more than one wizard with the same largest
     * number of students, the method throws an exception
     * @param c is the color
     * @return the wizard with the most students of color c
     * @throws ExceptionGame is thrown when there are more than one wizard with the same largest number of students of color c
     */
    public Wizard selectWizardWithMostStudents(Color c) throws ExceptionGame{
        HashMap<Wizard, Integer> students = new HashMap<>();
        for(Wizard w : wizards){
            students.put(w, w.getBoard().getStudentsFromTable(c).size());
        }
        Wizard wizard = Collections.max(students.entrySet(), (entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : 0 ).getKey();
        for(Wizard w : wizards){
            if(w.getBoard().getStudentsFromTable(c).size() == wizard.getBoard().getStudentsFromTable(c).size()  && !wizard.equals(w))
                throw new ExceptionGame("There are multiple wizards with the same number of students of color " +c);
        }
        return wizard;
    }

    /**
     *This method sets student drawn from studentBag on each cloud, the method throws an exception if
     * it is not possible to place students on the clouds
     * @throws ExceptionGame if it is not possible to sets students
     */
    public void setRandomStudentsOnCloud() throws ExceptionGame{
        for(Cloud c : clouds){
            c.setStudentsOnCloud(studentBag);
        }
    }

    /**
     * This method place the professor of color passed as parameter to the wizard with the largest number of students with that color,
     * the method throws an exception if it is not possible to determinate the wizard with most students of the color passed or if there
     * was an error in determinate which wizard was the older owner of the professor of the color passed
     * @param c is the color
     * @throws ExceptionGame is thrown if it is not possible to determinate the wizard with most students of the color passed or if there
     * was an error in determinate which wizard was the older owner of the professor of the color passed
     */
    public void placeProfessor(Color c) throws ExceptionGame{
        Wizard wizard = selectWizardWithMostStudents(c);
        for(Professor p : professors)
            if(p.getColor().equals(c)) {
                wizard.getBoard().setProfessorInTable(p);
                professors.remove(p);
                return;
            }
        for(Wizard w : wizards)
            if(w.getBoard().isProfessorPresent(c)){
                try {
                    Professor professor = w.getBoard().removeProfessorFromTable(c);
                    wizard.getBoard().setProfessorInTable(professor);
                }catch(ExceptionGame e){
                    throw new ExceptionGame("This wizard:" + w + ", does not have the professor of color" + c, e);
                }
            }

    }

    /**
     * This method place the student selected by the player to the passed archipelago, if the student is not
     * movable or the player is not in the game, the method throws an exception
     * @param player is the player
     * @param student is the student
     * @param archipelago is the archipelago
     * @throws ExceptionGame is thrown when the player is not associated a no wizard in the game or the student
     * not movable
     */
    public void placeStudentOnArchipelago(Player player, Student student, Archipelago archipelago) throws ExceptionGame{
            getWizardFromPlayer(player).placeStudentOnArchipelago(student, archipelago);
    }

    /**
     * This method place the student selected by the player to the board of the corresponding wizard,
     * if the student is not movable or the player is not in the game, the method throws an exception
     * @param player is the player
     * @param student is the student
     * @throws ExceptionGame is thrown when the player is not associated a no wizard in the game or the student
     * not movable
     */
    public void placeStudentOnTable(Player player, Student student) throws ExceptionGame{
        getWizardFromPlayer(player).placeStudentOnTable(student);
    }

    /**
     * This method manage the movement on the archipelagos of mother nature , it throws an exception if asked to move mother nature
     * into the same position she already was or if the archipelago passed is not in the game.
     * @param archipelago archipelago where the player wants to place mother nature
     * @throws ExceptionGame is thrown when the archipelago is not present in the game or mother nature
     * is asked to be placed in the same position where she was already
     */
    public void placeMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        if (archipelagos.contains(archipelago)) {
            int newPosition = archipelagos.indexOf(archipelago);
            int oldPosition = motherNature.getPosition();
            int numberOfSteps = ((newPosition + archipelagos.size()) - oldPosition) % archipelagos.size();
            Wizard wizard = getWizardFromPlayer(player);
            if( wizard.getRoundAssistantsCard() != null) {
                if (numberOfSteps >0 && numberOfSteps <= wizard.getRoundAssistantsCard().getStep()) {
                    archipelagos.get(oldPosition).setMotherNaturePresence(false);
                    motherNature.setPosition(newPosition);
                    archipelago.setMotherNaturePresence(true);
                }else if(oldPosition == newPosition && archipelagos.size() <= wizard.getRoundAssistantsCard().getStep()){
                    archipelagos.get(oldPosition).setMotherNaturePresence(false);
                    motherNature.setPosition(newPosition);
                    archipelago.setMotherNaturePresence(true);
                } else
                    throw new ExceptionGame("This archipelago is not allowed by the assistant's card");
            }else
                throw new ExceptionGame("Wizard does not have an Assistant's card");
        }else
            throw new ExceptionGame("This archipelago is not part of the game");
    }

    /**
     * This method move the students from the cloud passed to the game to the board of the wizard associated
     * to the player, if the move is not allowed the method throws an exception
     * @param player is the player
     * @param cloud is the cloud where are the students the player wants to move
     * @throws ExceptionGame is thrown if the move is not allowed
     */
    public void moveStudentFromCloudToBoard(Player player, Cloud cloud ) throws ExceptionGame{
        Wizard wizard = getWizardFromPlayer(player);
        Collection<Student> students = cloud.removeStudentFromCloud();
        wizard.placeStudentInEntrance(students);
    }

    /**
     * This method builds the tower of the wizard on the archipelago, each of them passed as parameter, an exception is thrown if there is an error
     * in the built of the towers
     * @param archipelago is the archipelago
     * @param wizard is the wizard
     * @throws ExceptionGame is thrown if there was an error in the built of the towers
     */
    public void buildTower( Wizard wizard, Archipelago archipelago) throws ExceptionGame{
        archipelago.placeWizardsTower(wizard);
    }

    /**
     * This method controls the correct merge of the archipelagos, starting from the one passed as parameter
     * if it is not possible to merge two archipelagos the method throws an exception
     * @param archipelago is the starting archipelagos
     */
    public void takeCareOfTheMerge(Archipelago archipelago){
        int actualIsle = archipelagos.indexOf(archipelago);

        try {
            Wizard wizardActualIsle = archipelago.getIsle().get(0).getTower().getProperty();

            try {
                int previousIsle = ((archipelagos.size() + actualIsle) - 1)%archipelagos.size();
                Wizard wizardPreviousIsle = archipelagos.get(previousIsle).getIsle().get(0).getTower().getProperty();
                if (wizardActualIsle.equals(wizardPreviousIsle)) {
                    archipelago.mergeArchipelago(archipelagos.get(previousIsle));
                    archipelagos.remove(archipelagos.get(previousIsle));
                    motherNature.setPosition(archipelagos.indexOf(archipelago));
                }
            } catch (ExceptionGame e1) {
                System.out.println("Previous Isle not controlled by any wizard");
            }
            try {
                int nextIsle = ((archipelagos.size() + actualIsle) + 1)%archipelagos.size();
                Wizard wizardNextIsle = archipelagos.get(nextIsle).getIsle().get(0).getTower().getProperty();
                if (wizardActualIsle.equals(wizardNextIsle)) {
                    archipelago.mergeArchipelago(archipelagos.get(nextIsle));
                    archipelagos.remove(archipelagos.get(nextIsle));
                    motherNature.setPosition(archipelagos.indexOf(archipelago));
                }
            } catch (ExceptionGame e2) {
                System.out.println("Next Isle is not controlled by any wizard");
            }
        }catch(ExceptionGame e0){
            System.out.println("The current Isle is not controlled by any wizard");
        }
    }

    /**
     * This method set randomly the first player of the match
     */
    public void setRandomlyFirstPlayer(){
        Random random = new Random();
        Collections.rotate(wizards, random.nextInt(wizards.size()));
    }

    /**
     * This method sets randomly on each entrance of the wizard's board, the maximum allowed number of students,
     * each of one is drawn from the studentBag
     */
    public void setRandomlyStudentsInEntrance(){
        Collection<Student> s = new ArrayList<>();
        for (Wizard w : wizards){
            while (w.getBoard().getStudentsInEntrance().size() + s.size() < limitOfStudentInEntrance){
                s.add(studentBag.drawStudent());
            }
            w.placeStudentInEntrance(s);
            s.removeAll(s);
        }
    }


    /**
     * This method returns the wizards of the game
     * @return wizards
     */
    public List<Wizard> getWizards() {
        return wizards;
    }
    /**
     * This method returns the clouds of the game
     * @return clouds
     */
    public Collection<Cloud> getClouds() {
        return clouds;
    }

    /**
     * This method returns the student bag of the game
     * @return student bag
     */
    public StudentBag getStudentBag() {
        return studentBag;
    }

    /**
     * This method returns the professors of the game
     * @return professors
     */
    public List<Professor> getProfessors() {
        return professors;
    }

    /**
     * This method returns the archipelagos of the game
     * @return archipelagos
     */
    public List<Archipelago> getArchipelagos() {
        return archipelagos;
    }

    /**
     * This method returns the mother nature of the game
     * @return mother nature
     */
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * This method returns the assistant's cards played during the round
     * @return a collection of AssistantsCard
     */
    public Collection<AssistantsCards> getAssistantsCardsPlayedInRound() {
        return assistantsCardsPlayedInRound;
    }

    public List<Wizard> getWizardsWithLeastTowers(){
        List<Wizard> wizardsWithLeastTowers = new ArrayList<>();
        Wizard comparator = wizards.get(0);
        int least_towers = comparator.getBoard().getTowersInBoard().size();
        for(int i = 1; i<wizards.size(); i++) {
            if (wizards.get(i).getBoard().getTowersInBoard().size() < least_towers) {
                least_towers = wizards.get(i).getBoard().getTowersInBoard().size();
                comparator = wizards.get(i);
            }
        }

        wizardsWithLeastTowers.add(comparator);
        for(int i = wizards.indexOf(comparator) + 1; i<wizards.size(); i++) {
            if (wizards.get(i).getBoard().getTowersInBoard().size() == least_towers) {
                wizardsWithLeastTowers.add(wizards.get(i));
            }
        }
        return wizardsWithLeastTowers;

    }

    @Override
    public Game clone() {
        try {
            Game clone = (Game) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "wizards=" + wizards +
                ", assistantsCardsPlayedInRound=" + assistantsCardsPlayedInRound +
                ", archipelagos=" + archipelagos +
                ", studentBag=" + studentBag +
                ", professors=" + professors +
                ", clouds=" + clouds +
                ", motherNature=" + motherNature +
                ", limitOfStudentInEntrance=" + limitOfStudentInEntrance +
                ", numOfStudentMovable=" + numOfStudentMovable +
                '}';
    }
}



