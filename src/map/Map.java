package map;

import map.cells.Cell;
import map.cells.CellDirection;
import map.cells.CellTypes;

import java.io.*;
import java.util.*;

public class Map {
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
            int lineNum = line.length();
            CellTypes cellTypes = null;
            ArrayList<CellDirection> directions = new ArrayList<>(2);
            for (int i = 0; i < lineNum; i+=2) {
                if (i == 0)
                    cellTypes = getCellTypeByCharacter(line.charAt(i));
                else
                    directions.add(getCellDirectionByCharacter(line.charAt(i)));
            }
            directions.trimToSize();
            Cell cell = new Cell(cellTypes, directions);
            printer.println(cell);
            addAnElementInCellList(cell);
        }
    }
    private List<Cell> cellList;
    private PrintStream printer;
    private HashMap<Cell, Cell> bridgeMap;
    private int highestXpos = 0;
    private int highestYpos = 0;
    private int lowestXpos = 0;
    private int lowestYpos = 0;

    public Map(PrintStream printer) {
        this.printer = printer;
        cellList = new ArrayList<>();
        bridgeMap = new HashMap<>();
    }

    public void createMap(String mapName) {
        MapBuilder mapBuilder = new MapBuilder(mapName);
        mapBuilder.streamMapData();
        buildBridges();
    }
    public void resetMap() {
        Cell.setCountCells(0);
        Cell.setXPosition(0);
        Cell.setYPosition(0);
        highestXpos = 0;
        highestYpos = 0;
        lowestXpos = 0;
        lowestYpos = 0;
        if (!cellList.isEmpty())
            cellList.clear();
    }
    public void printMap() {

    }
    public void printBridgesInfo() {
        for (java.util.Map.Entry<Cell, Cell> bridge : bridgeMap.entrySet())
            printer.println(bridge.toString());
    }
    public boolean movementCheck(Cell cell, String movements) {
        for (int i = 0; i < movements.length(); i++) {
            CellDirection cellDirection = getCellDirectionByCharacter(movements.charAt(i));
            int cellIndex = cell != null ? cell.getCELL_INDEX() : 0;
            printer.println(cellDirection);
            if (cell.isNextDirection(cellDirection)) {
                cell = getForwardCell(cellIndex);
                continue;
            }
            if (cell.isPreDirection(cellDirection)) {
                cell = getBackwardCell(cellIndex);
                continue;
            }
            printer.println("You can't move this way.");
            return false;
        }
        return true;
    }
    public void occurEventByCellType(Cell cell) {

    }
    private Cell getForwardCell(int i) {
        try {
            return cellList.get(i + 1);
        } catch (IndexOutOfBoundsException ignored) {}
        return null;
    }
    private Cell getBackwardCell(int i) {
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
            if (isBridgeStartCell(cell)) tempStartCellsList.add(cell);
            if (isBridgeEndCell(cell)) tempEndCellsList.add(cell);
        }
        buildBridgeByStartCellsAndEndCells(tempStartCellsList, tempEndCellsList);
    }
    private void buildBridgeByStartCellsAndEndCells(List<Cell> startCellsList, List<Cell> endCellsList) {
        for (Cell startCell : startCellsList) {
            for (Cell endCell : endCellsList) {
                if (isCellOnTheSameYaxis(endCell, startCell.getPOSITION().getXpos(), startCell.getPOSITION().getYpos()))
                    putBridgeInABridgeMap(startCell, endCell);
            }
        }
    }
    private boolean isBridgeStartCell(Cell cell) {
        return (cell.getCELL_TYPE() == CellTypes.BRIDGE_START);
    }
    private boolean isBridgeEndCell(Cell cell) {
        return (cell.getCELL_TYPE() == CellTypes.BRIDGE_END);
    }
    private boolean isCellOnTheSameYaxis(Cell cell, int x, int y) {
        return (cell.getPOSITION().getXpos() > x && cell.getPOSITION().getYpos() == y);
    }
    private void putBridgeInABridgeMap(Cell bridgeStart, Cell bridgeEnd) {
        bridgeMap.put(bridgeStart,bridgeEnd);
    }
    private void calcHighestLowestPositions(Cell cell) {
        highestXpos = Math.max(cell.getPOSITION().getXpos(), highestXpos);
        lowestXpos = Math.min(cell.getPOSITION().getXpos(), lowestXpos);
        highestYpos = Math.max(cell.getPOSITION().getYpos(), highestYpos);
        lowestYpos = Math.min(cell.getPOSITION().getYpos(), lowestYpos);
    }
    private void addAnElementInCellList(Cell cell) {
        calcHighestLowestPositions(cell);
        cellList.add(cell);
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
}



