package self.joanciscar.gamedevelopment;

import android.graphics.Paint;

public abstract class AbstractMovableEntity extends AbstractGameEntity implements MovableEntity {
    private final Paint painter = new Paint();
    private Vector position;
    public final double DECELERACION_POR_CHOQUE = 0.7;
    public final double DECELERACION_POR_ENTORNO = 0.99;
    private double mass = 1;
    private double xVelocity;
    private double yVelocity;
    private boolean movable = true;

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    @Override
    public boolean isMovable() {
        return movable;
    }
    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public boolean isMoving() {
        return 0 != xVelocity && 0 != yVelocity;
    }

    @Override
    public void stopMovement() {
        xVelocity = 0;
        yVelocity = 0;
    }

    @Override
    public double getxVelocity() {
        return xVelocity;
    }

    @Override
    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    @Override
    public double getyVelocity() {
        return yVelocity;
    }

    @Override
    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    @Override
    public void setVelocity(double xVelocity, double yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
}
