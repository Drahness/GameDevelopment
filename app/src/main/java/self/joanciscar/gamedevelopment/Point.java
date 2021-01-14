package self.joanciscar.gamedevelopment;

public class Point {
    private final double x;
    private final double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }
    public Point(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public Point(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public double distance(Point other) {
        return this.distance(other.x,other.y);
    }

    public double distance(float x2, float y2) {
        return distanceInBetweenTwoPoints(this.x,this.y,x2,y2);
    }
    public double distance(double x2, double y2) {
        return distanceInBetweenTwoPoints(this.x,this.y,x2,y2);
    }


    public static double distanceInBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static double distanceInBetweenTwoPoints(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static double distanceInBetweenTwoPoints(double x1, double y1, double x2,double y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        return Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     *
     * @param units moved to new position
     * @param begining the beggining position
     * @param direction the destination, this point will be an angle,
     * @return new position of the entity
     */
    public static Point newPosition(double units, Point begining, Point direction) {
        double alfa = Math.atan( (direction.y - begining.y) / (direction.x - begining.x));
        return new Point(units*Math.cos(alfa),units*Math.sin(alfa));
    }
}