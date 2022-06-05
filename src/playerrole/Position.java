package playerrole;
public class Position {
    private final int Xpos;
    private final int Ypos;
    public Position(int Xpos, int Ypos) {
        this.Xpos = Xpos;
        this.Ypos = Ypos;
    }
    private Position() {
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
}