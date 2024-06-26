package playerrole;

import playerrole.cells.Cell;
import playerrole.cells.CellDirection;
import playerrole.cells.CellTypes;

import java.io.*;
import java.util.*;

public class Board {
    private class MapBuilder {
        private final String MAP_DATA_DIRECTORY = "./map data/";
        private String mapName;
        private FileReader fileReader;
        private BufferedReader bufferedReader;
        private MapBuilder(String mapName) {
            this.mapName = mapName;
            fileReader = null;
            bufferedReader = null;
        }
        private void streamMapData() {
            File mapFile = new File(MAP_DATA_DIRECTORY + mapName + ".map");
            if (!mapFile.exists() || mapFile.isDirectory()) {
                printer.println("Wrong file name.");
                return;
            }
            printer.println(mapName + ".map is found.");
            try {
                fileReader = new FileReader(mapFile);
                bufferedReader = new BufferedReader(fileReader);
                printer.println(bufferedReader.toString() + " opened.");

                String line;
                int lineNum = 0;
                while((line = bufferedReader.readLine()) != null) {
                    //printer.printf("%d : %s - %d\n", ++lineNum, line, line.length());
                    createACellByOneLine(line);
                }
            } catch (IOException e) {
                printer.println("Wrong file reading.");
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                        printer.println(bufferedReader.toString() + " closed.");
                    }
                } catch (IOException e) {
                    printer.println(bufferedReader.toString() +" isn't closed.");
                }
            }
        }
        private void createACellByOneLine(String line) {
            int lineLength = line.length();
            CellTypes cellTypes = null;
            ArrayList<CellDirection> directions;
            Cell cell;
            if (!cellList.isEmpty()) {
                directions = new ArrayList<>(2);
                for (int i = 0; i < lineLength; i+=2) {
                    if (i == 0)
                        cellTypes = getCellTypeByCharacter(line.charAt(i));
                    else
                        directions.add(getCellDirectionByCharacter(line.charAt(i)));
                }
                cell = new Cell(cellTypes, directions);
            }
            else {
                directions = new ArrayList<>(1);
                directions.add(getCellDirectionByCharacter(line.charAt(2)));
                cell = new Cell(CellTypes.START, directions);
            }
            printer.println(cell);
            cellList.add(cell);
        }
    }
    private List<Cell> cellList;
    private PrintStream printer;
    private Map<Cell, Cell> bridgeMap;
    private MapPrinter mapPrinter;

    public Board(PrintStream printer) {
        this.printer = printer;
        cellList = new ArrayList<>();
        bridgeMap = new HashMap<>();
        mapPrinter = null;
    }
    public Board() {
        this.printer = new PrintStream(System.out);
        cellList = new ArrayList<>();
        bridgeMap = new HashMap<>();
        mapPrinter = null;
    }
    public void createMap() {
        resetMap();
        MapBuilder mapBuilder = new MapBuilder("default");
        mapBuilder.streamMapData();
        buildBridges();
        mapPrinter = new MapPrinter(cellList, bridgeMap);
    }
    public void createMap(String mapName) {
        resetMap();
        MapBuilder mapBuilder = new MapBuilder(mapName);
        mapBuilder.streamMapData();
        buildBridges();
        mapPrinter = new MapPrinter(cellList, bridgeMap);
    }
    private void resetMap() {
        Cell.setCountCells(0);
        Cell.setXPosition(0);
        Cell.setYPosition(0);
        mapPrinter = null;
        if (!cellList.isEmpty())
            cellList.clear();
        if (!bridgeMap.isEmpty())
            bridgeMap.clear();
    }
    public void printBoard(List<Player> players) {
        mapPrinter.print(players);
    }
    protected boolean movementCheck(Cell cell, String movements) {
        try {
            for (int i = 0; i < movements.length(); i++) {
                char c = movements.charAt(i);
                if (cell.isNextDirection(c))
                    cell = getForwardCell(cell.getCELL_INDEX());
                else if (cell.isPreDirection(c))
                    cell = getBackwardCell(cell.getCELL_INDEX());
                else
                    return false;
                if (Objects.requireNonNull(cell).getCELL_TYPE() == CellTypes.END)
                    return true;
            }
            return true;
        } catch (NullPointerException ignored) { }
        return false;
    }
    protected Cell getForwardCell(int i) {
        try {
            return cellList.get(i + 1);
        } catch (IndexOutOfBoundsException ignored) {}
        return null;
    }
    protected Cell getBackwardCell(int i) {
        try {
            return cellList.get(i - 1);
        } catch (IndexOutOfBoundsException ignored) {}
        return null;
    }
    private void buildBridges() {
        printer.println("Build bridges...");
        List<Cell> tempStartCellsList = new LinkedList<>();
        List<Cell> tempEndCellsList = new LinkedList<>();
        for (Cell cell : cellList) {
            if (cell.getCELL_TYPE() == CellTypes.BRIDGE_START) tempStartCellsList.add(cell);
            if (cell.getCELL_TYPE() == CellTypes.BRIDGE_END) tempEndCellsList.add(cell);
        }

        for (Cell startCell : tempStartCellsList) {
            for (Cell endCell : tempEndCellsList) {
                if (isCellOnTheSameYaxis(endCell, startCell.getPOSITION().XPOS, startCell.getPOSITION().YPOS)) {
                    printer.printf("StartCell:%s, EndCell:%s\n", startCell, endCell);
                    bridgeMap.put(startCell, endCell);
                }
            }
        }
    }
    private boolean isCellOnTheSameYaxis(Cell cell, int x, int y) {
        return (cell.getPOSITION().XPOS > x && cell.getPOSITION().YPOS == y);
    }
    private CellTypes getCellTypeByCharacter(char c) {
        for (CellTypes e : CellTypes.values()) {
            if (e.getaChar() == c)
                return e;
        }
        return null;
    }
    private CellDirection getCellDirectionByCharacter(char c) {
        for (CellDirection e : CellDirection.values()) {
            if (e.getaChar() == c)
                return e;
        }
        return null;
    }
    public List<Cell> getCellList() {
        return cellList;
    }
    public Map<Cell, Cell> getBridgeMap() {
        return bridgeMap;
    }
}

class MapPrinter {
    private int highestXPos = 0;
    private int highestYPos = 0;
    private int lowestXPos = 0;
    private int lowestYPos = 0;
    private List<Cell> cellList;
    private Map<Cell, Cell> bridgeMap;
    private char[][] printing;

    MapPrinter(List<Cell> cellList, Map<Cell, Cell> bridgeMap) {
        this.cellList = cellList;
        this.bridgeMap = bridgeMap;
        calcHighestLowestXY();
        printing = new char[highestYPos - lowestYPos+1][highestXPos-lowestXPos+1];
        setPrinting();
    }
    private void calcHighestLowestXY() {
        for (Cell cell : cellList) {
            highestXPos = Math.max(highestXPos, cell.getPOSITION().XPOS);
            lowestXPos = Math.min(lowestXPos, cell.getPOSITION().XPOS);
            highestYPos = Math.max(highestYPos, cell.getPOSITION().YPOS);
            lowestYPos = Math.min(lowestYPos, cell.getPOSITION().YPOS);
        }
    }
    private void setPrinting() {
        for (int i = lowestYPos; i <= highestYPos; i++) {
            for (int j = lowestXPos; j <= highestXPos; j++)
                printing[i-lowestYPos][j-lowestXPos] = ' ';
        }
        for (Cell cell : cellList) {
            printing[cell.getPOSITION().YPOS - lowestYPos][cell.getPOSITION().XPOS - lowestXPos]
                    = cell.getCELL_TYPE().getaChar();
        }
        for (java.util.Map.Entry<Cell, Cell> bridge : bridgeMap.entrySet()) {
            Cell startCell = bridge.getKey();
            Cell endCell = bridge.getValue();
            for (int i = startCell.getPOSITION().XPOS+1; i < endCell.getPOSITION().XPOS; i++) {
                printing[startCell.getPOSITION().YPOS-lowestYPos][i-lowestXPos] = '=';
            }
        }
    }
    protected void print(List<Player> players) {
        boolean isPrintPlayer = false;
        for (int i = lowestYPos-1; i <= highestYPos; i++) {
            for (int j = lowestXPos-1; j <= highestXPos; j++) {
                for (Player player : players) {
                    if (player.getPiece().getCurrentCell().getPOSITION().XPOS == j
                            && player.getPiece().getCurrentCell().getPOSITION().YPOS == i) {
                        System.out.printf("%2d", player.indexOfPlayer);
                        isPrintPlayer = true;
                        break;
                    }
                }//for players..END
                if (!isPrintPlayer) {
                    if (i == lowestYPos - 1) {
                        if (j != lowestXPos - 1)
                            System.out.printf("%2d", j);
                        else
                            System.out.print("  ");
                    } else {
                        if (j == lowestXPos - 1)
                            System.out.printf("%2d", i);
                        else
                            System.out.printf("%2c", printing[i - lowestYPos][j - lowestXPos]);
                    }
                }
                isPrintPlayer = false;
            }//for j..END
            System.out.println();
        }//for i..END
    }
}

