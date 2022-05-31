package playerrole;

import playerrole.cards.*;

import java.util.ArrayList;

public class Player {
    static int totalNumberOfPlayers = 0;
    private ArrayList<BridgeCard> bridgeCards;
    private ArrayList<EquipmentCard> equipmentCards;

    public Player() {
        bridgeCards = new ArrayList<>();
        equipmentCards = new ArrayList<>();
        totalNumberOfPlayers++;
    }
    private void addBridgeCard() {
        bridgeCards.add(new BridgeCard());
    }
    private void addEquipmentCard(EquipmentCardIndex e) {
        equipmentCards.add(new EquipmentCard(e.getName(), e.getScore()));
    }
    private void deleteBridgeCard() {
        if (!bridgeCards.isEmpty())
            bridgeCards.remove(bridgeCards.size()-1);
    }
    public static int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }
}
