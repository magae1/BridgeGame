package playerrole.cards;

public enum EquipmentCardIndex {
    P(1, "Philips Driver"), H(2, "Hammer"), S(3, "Saw");

    private final int score;
    private final String name;
    EquipmentCardIndex (int score,String name) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }
}
