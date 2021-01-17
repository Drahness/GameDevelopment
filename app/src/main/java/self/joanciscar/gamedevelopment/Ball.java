package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;

import java.util.List;

public class Ball extends AbstractMovableEntity {
    private final double radius;
    //private final String number;

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
        // Choque con la pared.
        if(betX != 0 || betY != 0) {
            if (betX == 1) {
                nextX -= (nextX - width);
                setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
            } else if (betX == -1) {
                nextX =  min_width;
                setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
            }
            if (betY == 1) {
                nextY -= (nextY - height);
                setyVelocity(-(getyVelocity() * DECELERACION_POR_CHOQUE));
            } else if (betY == -1) {
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
            //canvas.drawText();
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
                    // A hit is being produced or not?
                    if (isInContact(anotherBall)) {
                        this.transferEnergy(anotherBall);
                        this.move(height,width,min_height,min_width);
                        anotherBall.move(height,width, min_height, min_width);
                    }
                }
            }
        }
    }

    /**
     *  The old code of process dump, when dumping another ball only inverses the velocity.
     * @param gameEntity an entity, can be this and this will return.
     * @param height
     * @param width
     * @param min_height
     * @param min_width
     */
    @Deprecated
    public void simpleDump(GameEntity gameEntity, double height, double width, double min_height, double min_width) {
        if(gameEntity.isTangible()) {
            if(gameEntity.isMovable()) {
                if (gameEntity instanceof Ball) {
                    Ball anotherBall = (Ball) gameEntity;
                    // A hit is being produced or not?
                    if (this.getPosition().distance(anotherBall.getPosition()) < this.radius + anotherBall.radius && isTangible()) {
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
        double dx = anotherBall.getPosition().getX() - this.getPosition().getX();
        double dy = anotherBall.getPosition().getY() - this.getPosition().getY();
        double dist = this.getPosition().distance(anotherBall.getPosition());
        if (dist == 0) {
            dx = 0;
            dy = 0;
        } else {
            dx /= dist;
            dy /= dist;
        }
        double scale = (dx*this.getxVelocity()+dy*this.getyVelocity()) - (dx * anotherBall.getxVelocity() + dy * anotherBall.getyVelocity());
        this.setVelocity(
                this.getxVelocity()-(dx*scale),
                this.getyVelocity()-(dy*scale)
        );
        anotherBall.setVelocity(
                anotherBall.getxVelocity()+(dx*scale),
                anotherBall.getyVelocity()+(dy*scale)
        );
    }

    public boolean isInContact(Ball another) {
        if(!this.hasPosition() || !another.hasPosition()) {
            return false;
        }
        return this.getPosition().distance(another.getPosition()) < this.radius + another.radius && isTangible();
    }
    public static List<GameEntity> getBiliardBalls() {
        return null; //Todo
    }
}
