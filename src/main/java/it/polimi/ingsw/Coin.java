package it.polimi.ingsw;

public class Coin {
    private boolean onTable;


    /**
     *
     * @param onTable defined if the coin is on the table
     */
    public void setOnTable(boolean onTable) {
        this.onTable = onTable;
    }

    /**
     *
     * @return true if the coin is on the table
     */
    public boolean isOnTable() {
        return onTable;
    }
}
