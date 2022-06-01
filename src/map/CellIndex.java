package map;

enum CellTypes {
    START('S'), CELL('C'), END('E'),
    BRIDGE_START('B'), BRIDGE_END('b'),
    HAMMER('H'), SAW('S'), PHILIPS_DRIVER('P'),
    ;
    private final char type;
    CellTypes(char t) {
        type = t;
    }
    public char getType() {
        return type;
    }
}
enum CellDirection {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R')
    ;
    private final char direction;
    CellDirection(char d) {
        direction = d;
    }
    public char getDirection() {
        return direction;
    }
}