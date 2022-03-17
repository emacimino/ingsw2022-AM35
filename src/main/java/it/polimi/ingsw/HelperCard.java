package it.polimi.ingsw;

public enum HelperCard {
    private int Value, Step;
    private String Wizard;

    Card(int value, int step, String wizard) {
        Value = value;
        Step = step;
        Wizard = wizard;
    }

    public void setValue(int value) {
        Value = value;
    }

    public void setStep(int step) {
        Step = step;
    }

    public void setWizard(String wizard) {
        Wizard = wizard;
    }

    public int getValue() {
        return Value;
    }

    public int getStep() {
        return Step;
    }

    public String getWizard() {
        return Wizard;
    }
}
