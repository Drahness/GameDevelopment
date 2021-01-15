package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;
import android.graphics.Color;

public class Hole extends AbstractGameEntity {
    private static final double HOLE_RADIUS = 60;

    @Override
    public void paint(Canvas canvas) {
        this.getPainter().setColor(Color.BLACK);
        canvas.drawCircle((float) this.getPosition().getX(), (float) this.getPosition().getY(), (float) HOLE_RADIUS, this.getPainter());
    }


    @Override
    public void processDump(GameEntity gameEntity, double height, double width, double min_height, double min_width) {
        if(gameEntity.isTangible()) {
            if(gameEntity instanceof Ball) {
                gameEntity.setPosition(null);
                gameEntity.setTangible(false);
                gameEntity.setInGame(false);
            }
        }
    }
}
