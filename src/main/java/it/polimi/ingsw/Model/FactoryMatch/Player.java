package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Wizard.TowerColors;

public class Player {
    private final String username;
    private TowerColors towerColor;

    /**
     * constructor of Player Class
     * @param username username of the player
     */
    public Player(String username) {
        this.username = username;
    }

    /**
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the name of the player
     */


    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }

    private void setTowerColor(int indexOfPlayer) {
        switch (indexOfPlayer){
            case 1 -> this.towerColor = TowerColors.White;
            case 2 -> this.towerColor = TowerColors.Black;
            case 3 -> this.towerColor = TowerColors.Gray;
        }
    }

    public TowerColors getTowerColor(){
        return towerColor;
    }
}
