package self.joanciscar.gamedevelopment;

public class Ball {
    private Point position;
    private Point direction;

    public boolean isMoving() {
        return direction == null;
    }

    public void stopMovement() {
        direction = null;
    }

    public void startMovingTo(Point p) {
        this.direction = p;
    }

    public void move(float velocity) {


    }






}
