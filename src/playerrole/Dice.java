package playerrole;

import java.util.Random;

public class Dice {
    private final int MAX_NUM_OF_EYE;
    private final int MIN_NUM_OF_EYE;
    public Dice(int minEyeNumber, int maxEyeNumber) {
        MAX_NUM_OF_EYE = maxEyeNumber;
        MIN_NUM_OF_EYE = minEyeNumber;
    }
    public int roll() {
        return new Random().ints(1, MIN_NUM_OF_EYE,MAX_NUM_OF_EYE+1).findFirst().getAsInt();
    }
}
