package map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                System.out.println("Wrong file name.");
                return;
            }
            System.out.println(mapName + ".map is found.");
            try {
                fileReader = new FileReader(mapFile);
                bufferedReader = new BufferedReader(fileReader);
                System.out.println(bufferedReader.toString() + " opened.");

                String line;
                int lineNum = 0;
                while((line = bufferedReader.readLine()) != null) {
                    //System.out.printf("%d : %s - %d\n", ++lineNum, line, line.length());
                    createACellByOneLine(line);
                }
            } catch (IOException e) {
                System.out.println("Wrong file reading.");
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                        System.out.println(bufferedReader.toString() + " closed.");
                    }
                } catch (IOException e) {
                    System.out.println(bufferedReader.toString() +" isn't closed.");
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
            addAnElementInCellHashMap(new Cell(cellTypes, directions));
        }
        private CellTypes getCellTypeByCharacter(char c) {
            for (CellTypes e : CellTypes.values()) {
                if (e.getType() == c)
                    return e;
            }
            return null;
        }
        private CellDirection getCellDirectionByCharacter(char c) {
            for (CellDirection e : CellDirection.values()) {
                if (e.getDirection() == c)
                    return e;
            }
            return null;
        }
    }
    private HashMap<Integer, Cell> cellHashMap;
    private int highestXpos = 0;
    private int highestYpos = 0;
    private int lowestXpos = 0;
    private int lowestYpos = 0;

    public Map() {
        cellHashMap = new HashMap<>();
    }

    public void createMap(String mapName) {
        MapBuilder mapBuilder = new MapBuilder(mapName);
        mapBuilder.streamMapData();
    }

    public void resetMap() {
        Cell.setCountCells(0);
        Cell.setXPosition(0);
        Cell.setYPosition(0);
        highestXpos = 0;
        highestYpos = 0;
        lowestXpos = 0;
        lowestYpos = 0;
        if (!cellHashMap.isEmpty())
            cellHashMap.clear();
    }
    public HashMap<Integer, Cell> getCellHashMap() { return cellHashMap; }

    public void printMap() {

    }
    private void calcHighestLowestPositions(Cell cell) {
        highestXpos = Math.max(cell.getPOSITION().getXpos(), highestXpos);
        lowestXpos = Math.min(cell.getPOSITION().getXpos(), lowestXpos);
        highestYpos = Math.max(cell.getPOSITION().getYpos(), highestYpos);
        lowestYpos = Math.min(cell.getPOSITION().getYpos(), lowestYpos);
    }
    private void addAnElementInCellHashMap(Cell cell) {
        calcHighestLowestPositions(cell);
        cellHashMap.put(cell.getCELL_INDEX(), cell);
    }
}



