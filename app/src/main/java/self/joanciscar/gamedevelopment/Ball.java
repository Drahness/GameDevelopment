package self.joanciscar.gamedevelopment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ball extends AbstractMovableEntity {
    public static final float DEFAULT_RADIUS = 35;
    private static final int TEAM_RALLADA = 1;
    private static final int TEAM_LISA = 2;
    private static final int NEGRA = 3;
    private static final Paint blackPainter = new Paint();
    private static final Paint whitePainter = new Paint();
    private static final Paint borderPainter = new Paint();
    private Paint textPainter;
    private static final int STROKE_WIDTH = 5;
    private static final Random r = new Random();
    private static final int PURPLE = Color.argb(255, 214, 0, 255);
    private static final int BRWON = Color.argb(255, 255, 100, 0);
    private static final int ORANGE = Color.argb(255, 255, 200, 100);
    private final float radius;
    private String number;
    private int team;

    static {
        blackPainter.setColor(Color.BLACK);
        whitePainter.setColor(Color.WHITE);
        borderPainter.setColor(Color.BLACK);
        borderPainter.setStyle(Paint.Style.STROKE);
        borderPainter.setStrokeWidth(STROKE_WIDTH);
        blackPainter.setTextSize(35);
        blackPainter.setTextAlign(Paint.Align.CENTER);
        whitePainter.setTextSize(35);
        whitePainter.setTextAlign(Paint.Align.CENTER);
    }
    public Ball() {
        this(DEFAULT_RADIUS);
    }

    public Ball(float radius) {
        this.radius = radius;
    }

    public static List<GameEntity> getBiliardBalls(float minX, float minY, float maxX, float maxY) {
        List<GameEntity> balls = new ArrayList<>();
        Ball b;
        int x = 0;
        int ball = 0;
        Vector ballPosition;
        int color;
        float posX = minX + maxX / 2f;
        float posY = minY + maxY / 1.5f;
        for (int i = 1; i < 16; i++) {
            double posYBall = posY + (x* DEFAULT_RADIUS * 2) + 10;
            switch (i) {
                case 1: // LISA
                    color = Color.YELLOW;
                    ballPosition = new Vector(posX, posYBall);
                    x++;
                    break;
                case 2:
                    color = Color.BLUE;
                    ballPosition = new Vector(posX - (x * (DEFAULT_RADIUS)), posYBall);
                    break;
                case 3:
                    color = Color.RED;
                    ballPosition = new Vector(posX + (x * (DEFAULT_RADIUS)), posYBall);
                    x++;
                    break;
                case 4:
                    color = PURPLE;
                    posYBall = posY + (3* DEFAULT_RADIUS * 2) + 10; // correccion
                    ballPosition = new Vector(posX- (3 * (DEFAULT_RADIUS)), posYBall);
                    break;
                case 5:
                    color = ORANGE;
                    ballPosition = new Vector(posX + (x * (DEFAULT_RADIUS)), posYBall);
                    break;
                case 6:
                    color = Color.GREEN;
                    ballPosition = new Vector(posX - (x * (DEFAULT_RADIUS)), posYBall);
                    x++;
                    break; // CORRECTO

                case 7:
                    color = BRWON;
                    ballPosition = new Vector(posX - (DEFAULT_RADIUS), posYBall);
                    break;
                case 8: // NEGRA
                    color = Color.BLACK;
                    posYBall = posY + (2* DEFAULT_RADIUS * 2) + 10;
                    ballPosition = new Vector(posX, posYBall);
                    break;
                case 9: // RALLADA
                    color = Color.YELLOW;
                    ballPosition = new Vector(posX + (3 * (DEFAULT_RADIUS)), posYBall);
                    break;
                case 10:
                    color = Color.BLUE;
                    ballPosition = new Vector(posX + ((DEFAULT_RADIUS)), posYBall);
                    x++;
                    break;

                case 11: // 5 LINEA
                    color = Color.RED;
                    ballPosition = new Vector(posX, posYBall);
                    break;
                case 12: // (DEFAULT_RADIUS*2*2) == diametro + indice de la bola
                    color = PURPLE;
                    ballPosition = new Vector(posX + ((DEFAULT_RADIUS*2*2)), posYBall);
                    break;
                case 13:
                    color = ORANGE;
                    ballPosition = new Vector(posX + ((DEFAULT_RADIUS*2)), posYBall);
                    break;
                case 14:
                    color = Color.GREEN;
                    ballPosition = new Vector(posX - ((DEFAULT_RADIUS*2*2)), posYBall);
                    break;
                case 15:
                    color = BRWON;
                    ballPosition = new Vector(posX - ((DEFAULT_RADIUS*2)), posYBall);
                    break;
                default:
                    ballPosition = null;
                    color = 0;
            }
            b = new Ball();
            b.number = String.valueOf(i);
            if (i < 8) {
                b.team = TEAM_LISA;
            } else if (i > 8) {
                b.team = TEAM_RALLADA;
            } else {
                b.team = NEGRA;
            }
            b.changeColor(color);
            b.setPosition(ballPosition);
            balls.add(b);
        }
        return balls; //Todo
    }

    public void move(float height, float width, float min_height, float min_width) {
        height = height - radius;
        width = width - radius;
        min_height = min_height + radius;
        min_width = min_width + radius;
        float nextY = (this.getyVelocity()) + this.getPosition().getY();
        float nextX = (this.getxVelocity()) + this.getPosition().getX();
        int betX = Utils.isBetweenInt(nextX, min_width, width);
        int betY = Utils.isBetweenInt(nextY, min_height, height);
        // Choque con la pared.
        if (betX == 1) {
            nextX -= (nextX - width);
            setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
        }
        else if (betX == -1) {
            nextX = min_width;
            setxVelocity(-(getxVelocity() * DECELERACION_POR_CHOQUE));
        }
        if (betY == 1) {
            nextY -= (nextY - height);
            setyVelocity(-(getyVelocity() * DECELERACION_POR_CHOQUE));
        }
        else if (betY == -1) {
            nextY = min_height;
            setyVelocity(-(getyVelocity() * DECELERACION_POR_CHOQUE));
        }
        if (this.getVelocity().distance(new Vector(0, 0)) < 1) {
            this.stopMovement();
        } else {
            this.setVelocity(getxVelocity() * DECELERACION_POR_ENTORNO , getyVelocity() * DECELERACION_POR_ENTORNO);
        }
        this.setPosition(new Vector(nextX, nextY));
    }

    @Override
    public boolean isInContact(GameEntity another) {
        if(another instanceof Ball) {
            return this.isInContact((Ball) another);
        }
        return false;
    }

    @Override
    public void paint(Canvas canvas) {
        if (this.hasPosition()) {
            Vector pos = this.getPosition().getCopy();
            canvas.drawCircle((float) pos.getX(), (float) pos.getY(), (float) this.radius, this.getPainter());
            if (team != 0) {
                switch (team) {
                    case TEAM_RALLADA:
                        canvas.drawCircle((float) pos.getX(), (float) pos.getY(), (float) radius / 2, whitePainter);
                        break;
                    case NEGRA:
                        textPainter = whitePainter;
                }
            }
            if (number != null) {
                float textHeight = textPainter.descent() - textPainter.ascent();
                float textOffset = (textHeight / 2) - textPainter.descent();
                textPainter.setTextAlign(Paint.Align.CENTER);
                RectF bounds = new RectF(0, 0, (float) radius, (float) radius);
                canvas.drawText(number, (float) (pos.getX()/*-radius/2*/), (float) (pos.getY() + radius / 3), textPainter);
            }
            canvas.drawCircle((float) pos.getX(), (float) pos.getY(), (float) this.radius, borderPainter);
        }
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void processDump(GameEntity gameEntity, float height, float width, float min_height, float min_width,boolean callOther) {
        if (gameEntity.isTangible()) {
            if (gameEntity.isMovable()) {
                MovableEntity anotherBall = (MovableEntity) gameEntity;
                if (isInContact(anotherBall)) {
                    this.transferEnergy(anotherBall);
                    this.move(height, width, min_height, min_width);
                    anotherBall.move(height, width, min_height, min_width);
                    /*if(callOther) { Esto da mas problemas de los que soluciona

                        gameEntity.processDump(this, height, width, min_height, min_width, false);
                    }*/
                }

            }
        }
    }

    /**
     * The old code of process dump, when dumping another ball only inverses the velocity.
     *
     * @param gameEntity an entity, can be this and this will return.
     * @param height
     * @param width
     * @param min_height
     * @param min_width
     */
    @Deprecated
    public void simpleDump(GameEntity gameEntity, float height, float width, float min_height, float min_width) {
        if (gameEntity.isTangible()) {
            if (gameEntity.isMovable()) {
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
        float dx = anotherBall.getPosition().getX() - this.getPosition().getX();
        float dy = anotherBall.getPosition().getY() - this.getPosition().getY();
        float dist = this.getPosition().distance(anotherBall.getPosition());
        /*if (dist == 0) {
            dx = 0;
            dy = 0;
        } else {*/
        dx /= dist;
        dy /= dist;
        //}

        // transferir energia recursivamente, comprobar primero si estan siendo golpeadas y pasarle
        // el control a la bola golpeada para que se mueva
        float scale = (dx * this.getxVelocity() + dy * this.getyVelocity()) - (dx * anotherBall.getxVelocity() + dy * anotherBall.getyVelocity());
        float thisxvel = this.getxVelocity() - (dx * scale);
        float thisyvel = this.getyVelocity() - (dy * scale);
        float anotherxVel = anotherBall.getxVelocity() + (dx * scale);
        float anotheryVel = anotherBall.getyVelocity() + (dy * scale);
        this.setVelocity(
                this.getxVelocity() - (dx * scale),
                this.getyVelocity() - (dy * scale)
        );
        anotherBall.setVelocity(
                anotherBall.getxVelocity() + (dx * scale),
                anotherBall.getyVelocity() + (dy * scale)
        );
    }

    public boolean isInContact(Ball another) {
        if (!this.hasPosition() || !another.hasPosition()) {
            return false;
        }
        return this.getPosition().distance(another.getPosition()) <= this.radius + another.radius && isTangible();
    }

    @Override
    public void changeColor(int color) {
        super.changeColor(color);
        if(team == TEAM_LISA) {
            if(color == Color.RED  ||color == PURPLE ||color == Color.BLUE) {
                textPainter = whitePainter;
            }
            else {
                textPainter = blackPainter;
            }
        }
        else {
            textPainter = blackPainter;
        }
    }
}
