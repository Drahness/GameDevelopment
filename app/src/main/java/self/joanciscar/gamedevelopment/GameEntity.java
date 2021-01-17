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

    void processDump(GameEntity gameEntity, double height, double width, double min_height, double min_width,boolean callOther);
    default void processDump(GameEntity gameEntity, double height, double width, double min_height, double min_width) {
        this.processDump(gameEntity,height,width,min_height,min_width,true);
    }
    void setPosition(Vector position);

    boolean isInGame();

    void setInGame(boolean inGame);
}
