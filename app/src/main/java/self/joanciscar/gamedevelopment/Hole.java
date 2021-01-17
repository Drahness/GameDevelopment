package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;
import android.graphics.Color;

public class Hole extends AbstractGameEntity {
    private static final double HOLE_RADIUS = 50;

    @Override
    public void paint(Canvas canvas) {
        this.getPainter().setColor(Color.BLACK);
        canvas.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) HOLE_RADIUS, this.getPainter());
    }


    @Override
    public void processDump(GameEntity gameEntity, double height, double width, double min_height, double min_width, boolean callOther) {
        if(gameEntity.isTangible()) {
            if(gameEntity instanceof Ball) {
                Ball b = (Ball) gameEntity;
                if(gameEntity.hasPosition() && this.getPosition().distance(gameEntity.getPosition()) < (HOLE_RADIUS/2)+HOLE_RADIUS) {
                    if(!(b instanceof PlayerBall)) {
                        b.setPosition(null);
                        b.setTangible(false);
                        b.setInGame(false);
                    }
                    else {
                        b.stopMovement();
                        b.setPosition(Billaroid.playerInitialVector.getCopy());
                    }
                }
            }
        }
    }
}
