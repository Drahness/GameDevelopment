package self.joanciscar.gamedevelopment;

public interface MovableEntity extends GameEntity {
    public final float DECELERACION_POR_CHOQUE = 0.7f;
    public final float DECELERACION_POR_ENTORNO = 0.99f;
    float getMass();

    void setMass(float mass);

    void move(float height, float width, float min_height, float min_width);

    boolean isMoving();

    void stopMovement();

    Vector getVelocity();

    float getxVelocity();

    boolean isInContact(GameEntity another);

    default void setVelocity(Vector vector) {
        this.setxVelocity(vector.getX());
        this.setyVelocity(vector.getY());
    }

    default void setVelocity(float xVelocity, float yVelocity) {
        this.setxVelocity(xVelocity);
        this.setyVelocity(yVelocity);
    }

    void setxVelocity(float xVelocity);

    float getyVelocity();

    void setyVelocity(float yVelocity);

    void transferEnergy(MovableEntity movableEntity);

    float getSpeed();

    void setNextPosition();
}
