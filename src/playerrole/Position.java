package playerrole;
public class Position {
    public final int XPOS;
    public final int YPOS;
    public Position(int XPOS, int YPOS) {
        this.XPOS = XPOS;
        this.YPOS = YPOS;
    }
    private Position() {
        this(0,0);
    }
    public String toString() {
        return String.format("[%2d,%2d]", this.XPOS, this.YPOS);
    }
}