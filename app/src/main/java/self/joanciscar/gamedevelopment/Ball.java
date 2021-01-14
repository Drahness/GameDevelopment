package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private Point position;
    private final Paint painter = new Paint();
    private final double radius;
    private final double DECELERACION = 0.8;
    private double xVelocity;
    private double yVelocity;

    public Ball(double radius) {
        this.radius = radius;
    }

    public void paint(Canvas canvas) {
        canvas.drawCircle((float) position.getX(),(float) position.getY(),(float) this.radius, painter);
    }

    public void move(Canvas canvas) {
        double maxHeight = canvas.getHeight() - radius;
        double maxWitdh = canvas.getWidth() - radius;
        double minHeight = 0 + radius;
        double minWidth = 0 + radius;
        double nextY = yVelocity + position.getY();
        double nextX = xVelocity + position.getX();
        int betX = Utils.isBetweenInt(nextX,minWidth,maxWitdh);
        int betY = Utils.isBetweenInt(nextY,minHeight,maxHeight);
        if(betX == 1) {
            nextX -= (nextX - maxWitdh);
            setxVelocity(-(getxVelocity()*DECELERACION));
            }
        else if(betX == -1) { // TODO VA mal
            nextX = (nextX + minWidth);
            setxVelocity(-(getxVelocity()*DECELERACION));
        }
        if(betY == 1) {
            nextY -= (nextY - maxHeight);
            setyVelocity(-(getyVelocity()*DECELERACION));
        }
        else if(betY == -1) {  // TODO VA mal
            nextY = (nextY + minWidth);
            setyVelocity(-(getyVelocity()*DECELERACION));
        }
        this.position = new Point(nextX,nextY);
        this.paint(canvas);
    }
    public boolean isMoving() {
        return 0 == xVelocity && 0 == yVelocity;
    }

    public void stopMovement() {
        xVelocity = 0;
        yVelocity = 0;
    }

    public Point getPosition() {
        return position;
    }

    public Paint getPainter() {
        return painter;
    }

    public double getRadius() {
        return radius;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean hasPosition() {
        return position != null;
    }

    public void transferEnergy(Ball anotherBall) {
        anotherBall.setxVelocity(-(anotherBall.getxVelocity()+this.getxVelocity()*DECELERACION));
        anotherBall.setyVelocity(-(anotherBall.getyVelocity()+this.getyVelocity()*DECELERACION));
        this.setxVelocity(-(this.getxVelocity()*0.10));
        this.setxVelocity(-(this.getxVelocity()*0.10));

         // Maybe todo its a lot of simple
    }

    public void processDump(Ball anotherBall) {
        //if() // TODO
    }
}
/*
 TODO https://math.stackexchange.com/questions/260096/find-the-coordinates-of-a-point-on-a-circle
 TODO https://www.mathsisfun.com/algebra/trig-finding-angle-right-triangle.html
 */