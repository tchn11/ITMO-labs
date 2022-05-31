package msplab4;

public class ValidPoint {
    public Point checkPoint(float x, float y, float r) throws IllegalArgumentException {
        checkCorrect(x, y, r);
        boolean result = ((x<=r) && (x<=0) && (y<=r) && (y>=0) && (Math.pow(x, 2)+Math.pow(y, 2)<=Math.pow(r, 2))) ||
                ((x>=0) &&(y<=0) && (y<=r/2)  && (x<=r)) || ((x<=0) &&(y<=0) && (x<=r) &&(y<=r));
        return new Point(x, y, r, result);
    }
    private void checkCorrect(float x, float y, float r) throws IllegalArgumentException {
        if (-4 >= x || x >= 4) {throw new IllegalArgumentException("Incorrect value x");}
        if (-5 >= y || y >= 5) { throw new IllegalArgumentException("Incorrect value y");}
        if (1 >= r || r >= 4) {throw new IllegalArgumentException("Incorrect value r");}
    }
}
