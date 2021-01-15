package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;

import java.util.List;

public class Ball extends AbstractMovableEntity {
    private final double radius;
    private final String number;

    public Ball(double radius) {
        this.radius = radius;
    }

    public void move(double height, double width,double min_height, double min_width) {
        height = height - radius;
        width = width - radius;
        min_height = min_height + radius;
        min_width = min_width + radius;
        double nextY = (this.getyVelocity()) + this.getPosition().getY();
        double nextX = (this.getxVelocity()) + this.getPosition().getX();
        int betX = Utils.isBetweenInt(nextX, min_width, width);
        int betY = Utils.isBetweenInt(nextY, min_height, height);
        if(betX != 0 || betY != 0) {
            if (betX == 1) {
                nextX -= (nextX - width);
                setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
            } else if (betX == -1) { // TODO VA mal
                nextX =  min_width;
                setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
            }
            if (betY == 1) {
                nextY -= (nextY - height);
                setyVelocity(-(getyVelocity() * DECELERACION_POR_CHOQUE));
            } else if (betY == -1) {  // TODO VA mal
                nextY = min_height;
                setyVelocity(-(getyVelocity() * DECELERACION_POR_CHOQUE));
            }
        }
        else {
            setVelocity(getxVelocity() * DECELERACION_POR_ENTORNO,getyVelocity() * DECELERACION_POR_ENTORNO);
        }

        this.setPosition(new Vector(nextX, nextY));
    }

    @Override
    public void paint(Canvas canvas) {
        if(this.hasPosition()) {
            canvas.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) this.radius, this.getPainter());
            canvas.drawText();
        }
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void processDump(GameEntity gameEntity, double height, double width, double min_height, double min_width) {
        if(gameEntity.isTangible()) {
            if(gameEntity.isMovable()) {
                if (gameEntity instanceof Ball) {
                    Ball anotherBall = (Ball) gameEntity;
                    if (this.getPosition().distance(anotherBall.getPosition()) < this.radius + anotherBall.radius && isTangible()) {
                        // A hit is being produced.
                        this.setVelocity(-this.getxVelocity(), -this.getyVelocity());
                        anotherBall.setVelocity(-anotherBall.getxVelocity(), -anotherBall.getyVelocity());
                        do { // Todo rework this or create a runnable of this.
                            this.move(height, width, min_height, min_width);
                            anotherBall.move(height, width, min_height, min_width);
                        } while (this.getPosition().distance(anotherBall.getPosition()) <= this.radius + anotherBall.radius);
                    }
                }
            }
        }
    }

    public void transferEnergy(MovableEntity anotherBall) {
        anotherBall.setxVelocity(-(anotherBall.getxVelocity() + this.getxVelocity() * DECELERACION_POR_CHOQUE));
        anotherBall.setyVelocity(-(anotherBall.getyVelocity() + this.getyVelocity() * DECELERACION_POR_CHOQUE));
        this.setxVelocity(-(this.getxVelocity() * 0.10));
        this.setxVelocity(-(this.getxVelocity() * 0.10));
    }

    public List<GameEntity> getBiliardBalls() {

    }
}
