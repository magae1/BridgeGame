package playerrole.cells;

public enum CellTypes {
    HAMMER('H'), SAW('S'), PHILIPS_DRIVER('P'),
    START('S'), CELL('C'), END('E'),
    BRIDGE_START('B'), BRIDGE_END('b'),
    ;
    private final char aChar;
    CellTypes(char t) {
        aChar = t;
    }
    public char getaChar() {
        return aChar;
    }
}