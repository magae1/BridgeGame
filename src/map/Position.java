package map;
public class Position {
    private final int Xpos;
    private final int Ypos;
    public Position(int Xpos, int Ypos) {
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }
    Position() {
        this(0,0);
    }

    public int getXpos() {
        return Xpos;
    }
    public int getYpos() {
        return Ypos;
    }
    public String toString() {
        return String.format("[%2d,%2d]", this.getXpos(), this.getYpos());
    }
    public static String toString(Position position) {
        return String.format("[%2d,%2d]", position.getXpos(), position.getYpos());
    }
    public boolean isEqual(int x, int y) {
        return (this.Xpos == x && this.Ypos == y);
    }
}