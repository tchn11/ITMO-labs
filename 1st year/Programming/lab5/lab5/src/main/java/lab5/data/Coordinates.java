package lab5.data;


/**
 * X-Y coordinates
 */
public class Coordinates {
    private long x; //Максимальное значение поля: 752
    private int y;

    /**
     * Constructor, just set x, y
     * @param X X coordinate
     * @param Y Y coordinate
     */
    public Coordinates(long X, int Y){
        x = X;
        y = Y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + Long.toString(x) +
                ", y=" + Integer.toString(y) +
                '}';
    }
}
