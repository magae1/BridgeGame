package playerrole.cards;

public class EquipmentCard extends Card {
    private final String NAME;
    private final int SCORE;

    public EquipmentCard(String name, int score) {
        super();
        NAME = name;
        SCORE= score;
    }
    public EquipmentCard(EquipmentCardIndex e) {
        this(e.getName(), e.getScore());
    }
    public int getScore() {
        return SCORE;
    }
    String getName() {
        return NAME;
    }
}
