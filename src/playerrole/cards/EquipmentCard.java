package playerrole.cards;

public class EquipmentCard extends Card {
    private String name;
    private int score;

    public EquipmentCard(String name, int score) {
        super();
        this.name = name;
        this.score= score;
    }
    int getScore() {
        return score;
    }
    String getName() {
        return name;
    }
}
