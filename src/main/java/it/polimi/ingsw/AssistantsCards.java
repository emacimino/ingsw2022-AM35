package it.polimi.ingsw;

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
     *
     * @param assistantCard
     * @return the value of one card
     */
    private double getValue(AssistantsCards assistantCard) { return value; }

    /**
     *
     * @param assistantCard
     * @return the step that MotherNature can do by playing that card
     */
    private double getStep(AssistantsCards assistantCard) { return step; }
}
