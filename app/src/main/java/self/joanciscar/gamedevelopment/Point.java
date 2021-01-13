package self.joanciscar.gamedevelopment;

public class Point {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }
    public double distance(Point other) {
        return this.distance(other.x,other.y);
    }
    public double distance(float x2, float y2) {
        return distanceInBetweenTwoPoints(this.x,this.y,x2,y2);
    }


    public static double distanceInBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static double distanceInBetweenTwoPoints(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    public static Point newPosition(float velocity, Point begining, Point direction) {

    }
}
