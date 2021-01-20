package self.joanciscar.gamedevelopment;

import android.graphics.Point;

/**
 * Representa un vector que puede ser la posicion o la velocidad.
 */
public class Vector {
    private float x;
    private float y;
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static float distanceInBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        return Vector.distanceInBetweenTwoPoints((double) x1, y1, x2, y2);
    }

    public static float distanceInBetweenTwoPoints(float x1, float y1, float x2, float y2) {
        return Vector.distanceInBetweenTwoPoints((double) x1, y1, x2, y2);
    }

    public static float distanceInBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }

    public double getAngleTo(Vector other) {
        return Vector.getAngle(this,other);
    }

    public static float getAngle(Vector v1, Vector v2) {
        double alfa = Math.atan2((v1.y - v2.y),(v2.x - v2.x));
        return (float) Math.toDegrees(alfa);
    }

    public Vector newVelocity(float units, Vector direction) {
        return Vector.newVelocity(units,this,direction);
    }
    public static Vector newVelocity(float units, Vector begining, Vector direction) {
        float alfa = (float) Math.atan2((direction.y - begining.y),(direction.x - begining.x));
        //boolean inverseX = false, inverseY = false;
        /*if(begining.getX() > direction.getX()) {
            inverseX = true;
        }
        if(begining.getY() > direction.getY()) {
            inverseY = true;
        }*/
        float x = (float) (units * Math.cos(alfa));
        float y = (float) (units * Math.sin(alfa));
        /*return new Vector(
                inverseX ? - x: x,
                inverseY ? - y: y);*/
        return new Vector(x, y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float distance(Vector other) {
        return this.distance(other.x, other.y);
    }

    public float distance(float x2, float y2) {
        return distanceInBetweenTwoPoints(this.x, this.y, x2, y2);
    }

    public float distance(double x2, double y2) {
        return distanceInBetweenTwoPoints(this.x, this.y, x2, y2);
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
        if (!(o instanceof Vector)) return false;

        Vector point = (Vector) o;

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

    public Vector add(Vector anVector) {
        this.addX(anVector.getX());
        this.addY(anVector.getY());
        return this;
    }

    public Vector sub(Vector anVector) {
        this.setX(this.getX() - anVector.getX());
        this.setY(this.getX() - anVector.getX());
        return this;
    }

    public Vector inverse() {
        this.inverseX();
        this.inverseY();
        return this;
    }

    public Vector inverseX() {
        this.setX(-this.getX());
        return this;
    }

    public Vector inverseY() {
        this.setY(-this.getY());
        return this;
    }

    public Vector mult(float multiplier) {
        this.setX(this.getX() * multiplier);
        this.setY(this.getY() * multiplier);
        return this;
    }

    public boolean xGreaterThan(Vector x) {
        return this.getX() > x.getX();
    }
    public boolean yLowerThan(Vector x) {
        return this.getY() < x.getY();
    }
    public boolean yGreaterThan(Vector x) {
        return this.getY() > x.getY();
    }
    public boolean xLowerThan(Vector x) {
        return this.getX() < x.getX();
    }
    public Vector getCopy() {
        return new Vector(this.getX(), this.getY());
    }
    public Vector copyOf(Vector another) {
        this.setX(another.getX());
        this.setY(another.getY());
        return this;
    }
    public Vector addX(float x) {
        this.setX(this.getX()+x);
        return this;
    }
    public Vector addY(float y) {
        this.setY(this.getY()+y);
        return this;
    }

}