package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;

import java.util.List;

public class Wall extends AbstractGameEntity {

    private static float max_width;
    private static float max_height;
    private float width;
    private float height;

    private Wall(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Canvas canvas) {

    }

    @Override
    public void processDump(GameEntity gameEntity, float height, float width, float min_height, float min_width, boolean callOther) {
        if(gameEntity.isMovable()) {
            MovableEntity me = ((MovableEntity) gameEntity);
            float nextY = (me.getyVelocity()) + me.getPosition().getY();
            float nextX = (me.getxVelocity()) + me.getPosition().getX();
            int betX = Utils.isBetweenInt(nextX, min_width, width);
            int betY = Utils.isBetweenInt(nextY, min_height, height);
            if (betX == 1) {
                nextX -= (nextX - width);
                me.setxVelocity(-(me.getxVelocity() * me.DECELERACION_POR_CHOQUE));
            } else if (betX == -1) {
                nextX = min_width;
                me.setxVelocity(-(me.getxVelocity() * me.DECELERACION_POR_CHOQUE));
            }
            if (betY == 1) {
                nextY -= (nextY - height);
                me.setyVelocity(-(me.getyVelocity() * me.DECELERACION_POR_CHOQUE));
            } else if (betY == -1) {
                nextY = min_height;
                me.setyVelocity(-(me.getyVelocity() * me.DECELERACION_POR_CHOQUE));
            }
            this.setPosition(new Vector(nextX, nextY));
        }
    }

    public static List<GameEntity> getWalls(float max_width, float max_height) {
        Wall.max_width = max_width;
        Wall.max_height = max_height;

        return null;
    }
}
