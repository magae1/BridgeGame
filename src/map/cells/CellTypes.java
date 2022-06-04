package map.cells;

public enum CellTypes {
    START('S'), CELL('C'), END('E'),
    BRIDGE_START('B'), BRIDGE_END('b'),
    HAMMER('H'), SAW('S'), PHILIPS_DRIVER('P')
    ;
    private final char aChar;
    CellTypes(char t) {
        aChar = t;
    }
    public char getaChar() {
        return aChar;
    }
}