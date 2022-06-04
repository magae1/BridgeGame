package playerrole;

import java.util.Random;

public class Dice {
    private static final int MAX_NUM_OF_EYE = 6;
    private static final int MIN_NUM_OF_EYE = 1;
    public Dice() {
    }
    public static int roll() {
        return new Random().ints(1, MIN_NUM_OF_EYE,MAX_NUM_OF_EYE+1).findFirst().getAsInt();
    }
    public static int getMaxNumOfEye() {
        return MAX_NUM_OF_EYE;
    }
    public static int getMinNumOfEye() {
        return MIN_NUM_OF_EYE;
    }
}
