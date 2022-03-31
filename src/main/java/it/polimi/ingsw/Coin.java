package it.polimi.ingsw;

public class Coin {
    private Wizard property;
    private boolean onTable;

    public void setProperty(Wizard property) {
        this.property = property;
    }

    public void setOnTable(boolean onTable) {
        this.onTable = onTable;
    }

    public Wizard getProperty() {
        return property;
    }

    public boolean isOnTable() {
        return onTable;
    }
}
