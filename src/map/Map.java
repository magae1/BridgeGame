package map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Map {
    class MapBuilder {
        private final String MAP_DATA_DIRECTORY = "./map data/";
        String mapName;
        FileReader fileReader;
        BufferedReader bufferedReader;

        MapBuilder(String mapName) {
            this.mapName = mapName;
            fileReader = null;
            bufferedReader = null;
        }
        void build() {
            File mapFile = new File(MAP_DATA_DIRECTORY + mapName + ".map");
            if (!mapFile.exists() || mapFile.isDirectory()) {
                System.out.println("Wrong file name.");
                return;
            }
            try {
                fileReader = new FileReader(mapFile);
                bufferedReader = new BufferedReader(fileReader);

                int data;
                while((data = bufferedReader.read()) != -1) {
                    System.out.print((char)data);
                }


            } catch (IOException e) {
                System.out.println("Wrong file reading.");
            } finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("BufferedReader isn't closed.");
                }
            }
        }

    }

    HashMap<Position, Cell> cellHashMap;
    private final String MAP_1 = "default";
    private final String MAP_2 = "another";

    public void createMap() {
        MapBuilder builder = new MapBuilder(MAP_1);
        builder.build();
    }


}

class Position {
    private int Xpos;
    private int Ypos;
    Position(int Xpos, int Ypos) {
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }
    Position() {
        this(0,0);
    }

    int getXpos() {
        return Xpos;
    }

    void setXpos(int xpos) {
        Xpos = xpos;
    }

    int getYpos() {
        return Ypos;
    }

    void setYpos(int ypos) {
        Ypos = ypos;
    }
}