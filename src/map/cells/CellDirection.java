package map.cells;

public enum CellDirection {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R')
    ;
    private final char aChar;
    CellDirection(char d) {
        aChar = d;
    }
    public char getaChar() {
        return aChar;
    }
}