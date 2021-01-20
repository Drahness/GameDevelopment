package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface GameEntity {
    boolean isTangible();

    void setTangible(boolean dumpable);

    void paint(Canvas canvas);

    boolean isMovable();

    void changeColor(int color);

    Vector getPosition();

    Paint getPainter();

    boolean hasPosition();

    void processDump(GameEntity gameEntity, float height, float width, float min_height, float min_width,boolean callOther);
    default void processDump(GameEntity gameEntity, float height, float width, float min_height, float min_width) {
        this.processDump(gameEntity,height,width,min_height,min_width,true);
    }
    void setPosition(Vector position);

    boolean isInGame();

    void setInGame(boolean inGame);
}
