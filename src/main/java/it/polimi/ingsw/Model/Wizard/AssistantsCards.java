package it.polimi.ingsw.Model.Wizard;

/**
 * Instantiate all the AssistantCard
 */
public enum AssistantsCards {
        CardOne(1,1),
        CardTwo(2,1),
        CardThree(3,2),
        CardFour(4,2),
        CardFive(5,3),
        CardSix(6,3),
        CardSeven(7,4),
        CardEight(8,4),
        CardNine(9,5),
        CardTen(10,5);

        private final int value;
        private final int step;

        AssistantsCards(int value, int step) {
            this.value = value;
            this.step = step;
        }

    /**
     * @return the value of one card
     */
    public double getValue() { return value; }

    /**
     * @return the step that MotherNature can do by playing that card
     */
    public double getStep() { return step; }
}
