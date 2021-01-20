package self.joanciscar.gamedevelopment;


import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractGameEntity implements GameEntity {
    private final Paint painter = new Paint();
    private Vector position;
    private int color = Color.BLACK;
    private boolean isDumpable = true;
    private boolean inGame = true;


    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isTangible() {
        return isDumpable;
    }

    @Override
    public void setTangible(boolean dumpable) {
        isDumpable = dumpable;
    }

    @Override
    public void changeColor(int color) {
        this.color = color;
        painter.setColor(color);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public Paint getPainter() {
        return painter;
    }

    @Override
    public boolean hasPosition() {
        return position != null;
    }

    @Override
    public boolean isInGame() {
        return inGame;
    }
    @Override
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return color + " / " + ((position != null) ? position.toString() : "null") + " - r";
    }
}