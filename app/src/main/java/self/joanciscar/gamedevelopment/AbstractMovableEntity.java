package self.joanciscar.gamedevelopment;

import android.graphics.Paint;

public abstract class AbstractMovableEntity extends AbstractGameEntity implements MovableEntity {
    private final Paint painter = new Paint();
    private Vector position;
    private final Vector velocity = new Vector(0,0);
    public final double DECELERACION_POR_CHOQUE = 0.7;
    public final double DECELERACION_POR_ENTORNO = 0.99;
    private double mass = 1;
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
        return 0 != this.getxVelocity() && 0 != this.getyVelocity();
    }

    @Override
    public void stopMovement() {
        this.setyVelocity(0);
        this.setxVelocity(0);
    }

    @Override
    public double getxVelocity() {
        return this.velocity.getX();
    }

    @Override
    public void setxVelocity(double xVelocity) {
        this.velocity.setX(xVelocity);
    }

    @Override
    public double getyVelocity() {
        return this.velocity.getY();
    }

    @Override
    public void setyVelocity(double yVelocity) {
        this.velocity.setY(yVelocity);
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public double getSpeed() {
        return Math.hypot(getxVelocity(),getyVelocity());
    }
}
