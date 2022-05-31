package msplab4;

public class Point {
    private final float x;
    private final float y;
    private final float r;
    private final boolean b;

    public Point(float x, float y, float r, boolean b) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.b = b;
    }
    public boolean status() {
        return b;
    }

    @Override
    public String toString() {
//        String ans = "yes";
//        if (!b){ ans = "no"; }
//        System.out.println("x = "+x+" y = "+y+" r = "+r+ ans);
        return String.format("x = %s \ny = %s \nr = %s \nstatus: %s", x, y, r, b ? "yes" : "no");
    }

}
