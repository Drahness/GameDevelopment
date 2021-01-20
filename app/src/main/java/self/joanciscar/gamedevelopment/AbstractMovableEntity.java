package self.joanciscar.gamedevelopment;

import android.graphics.Paint;

public abstract class AbstractMovableEntity extends AbstractGameEntity implements MovableEntity {
    private final Paint painter = new Paint();
    private Vector position;
    private final Vector velocity = new Vector(0,0);
    private float mass = 1;
    private boolean movable = true;

    public void setMovable(boolean movable) {
        this.movable = movable;
    }


    @Override
    public boolean isMovable() {
        return movable;
    }
    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public void setMass(float mass) {
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
    public float getxVelocity() {
        return this.velocity.getX();
    }

    @Override
    public void setxVelocity(float xVelocity) {
        this.velocity.setX(xVelocity);
    }

    @Override
    public float getyVelocity() {
        return this.velocity.getY();
    }

    @Override
    public void setyVelocity(float yVelocity) {
        this.velocity.setY(yVelocity);
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public float getSpeed() {
        return (float) Math.hypot(getxVelocity(),getyVelocity());
    }

    @Override
    public void setNextPosition() {
        this.setPosition(new Vector(this.getxVelocity(),this.getyVelocity()));
    }
}
