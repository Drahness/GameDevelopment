package self.joanciscar.gamedevelopment;

public interface MovableEntity extends GameEntity {
    double getMass();

    void setMass(double mass);

    void move(double height, double width, double min_height, double min_width);

    boolean isMoving();

    void stopMovement();

    double getxVelocity();

    default void setVelocity(Vector vector) {
        this.setxVelocity(vector.getX());
        this.setyVelocity(vector.getY());
    }

    default void setVelocity(double xVelocity, double yVelocity) {
        this.setxVelocity(xVelocity);
        this.setyVelocity(yVelocity);
    }

    void setxVelocity(double xVelocity);

    double getyVelocity();

    void setyVelocity(double yVelocity);


    void transferEnergy(MovableEntity movableEntity);
}
