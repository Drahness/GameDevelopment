package self.joanciscar.gamedevelopment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

public class Billaroid extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread billaroidThread;
    private final Paint backgroundPainter = new Paint();
    private final Paint blackPainter = new Paint();
    private int height;
    private int width;
    private static final float MAX_DISTANCE_THROW = 500;
    private static final float POWER_UNIT = 15;
    private static final float MAX_POWER_THROW = MAX_DISTANCE_THROW / POWER_UNIT ;
    public static Vector playerInitialVector;
    private static final float BOARD_WALLS_HEIGHT = 75;
    private static final float BOARD_WALLS_WIDTH = 75;
    private final Random r = new Random();
    private static boolean isInitiated = false;
    private final PlayerBall playerBall = new PlayerBall();
    private final ArrayList<GameEntity> entities = new ArrayList<>();

    public Billaroid(Context context) {
        super(context);
        this.getHolder().addCallback(this);
        blackPainter.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = (int) (h-BOARD_WALLS_WIDTH);
        this.width = (int) (w-BOARD_WALLS_WIDTH);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.billaroidThread = new Billaroid.GameThread(holder);
        backgroundPainter.setColor(Color.BLUE);
        backgroundPainter.setStyle(Paint.Style.FILL);
        billaroidThread.start();
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.billaroidThread.stop = true;
    }


    @SuppressWarnings("final")
    private class GameThread extends Thread {
        private final SurfaceHolder holder;
        public boolean stop;
        public GameThread(SurfaceHolder surfaceHolder) {
            this.holder = surfaceHolder;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            super.run();
            while (!stop) {
                if(height != 0 && width != 0) {
                    if(!isInitiated) {
                        initGame();
                        isInitiated = true;
                    }
                    for (GameEntity b : entities) {
                        for (GameEntity gameEntity : entities) { // ponerlo arriba de isMovable()
                            if (gameEntity != b && gameEntity.hasPosition() && gameEntity.isTangible()) {
                                b.processDump(gameEntity, height, width, BOARD_WALLS_HEIGHT, BOARD_WALLS_WIDTH);
                            }
                        }
                    }
                    for (GameEntity gameEntity: entities) {
                        if(gameEntity.isInGame() && gameEntity.isMovable()) {
                            MovableEntity entity = (MovableEntity) gameEntity;
                            if(entity.isMoving()) {
                                ((MovableEntity) gameEntity).move(height, width, BOARD_WALLS_HEIGHT, BOARD_WALLS_WIDTH);
                            }
                        }
                    }
                }
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas(null);
                    if(height == 0) {
                        height = canvas.getHeight();
                    }
                    if(width == 0) {
                        width = canvas.getWidth();
                    }
                    synchronized (holder) {
                        newDraw(canvas);
                    }
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(playerBall.hasFingerPressed()) {
                    float distance = playerBall.getPosition().distance(this.playerBall.getFingerPosition());
                    float powerOfThrow;
                    if (distance > MAX_DISTANCE_THROW) {
                        powerOfThrow = MAX_POWER_THROW;
                    } else {
                        powerOfThrow = distance / POWER_UNIT;
                    }
                    playerBall.hitBall(powerOfThrow);
                }
                playerBall.changeColor(Color.WHITE);
                playerBall.setFingerActive(false);
                break;
            case MotionEvent.ACTION_MOVE: // mantener pulsado
                if(playerBall.hasFingerPressed()) {
                    if (event.getHistorySize() > 0) {
                        playerBall.setFingerPosition(new Vector(event.getHistoricalX(event.getHistorySize() - 1), event.getHistoricalY(event.getHistorySize() - 1)));
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                double minX, maxX;
                double minY, maxY;
                double x = event.getX();
                double y = event.getY();
                maxX = playerBall.getPosition().getX() + playerBall.getRadius() + 30;
                minX = playerBall.getPosition().getX() - playerBall.getRadius() - 30;
                maxY = playerBall.getPosition().getY() + playerBall.getRadius() + 30;
                minY = playerBall.getPosition().getY() - playerBall.getRadius() - 30;
                if(Utils.isBetween(x,minX,maxX) && Utils.isBetween(y,minY,maxY)) {
                    playerBall.setFingerActive(true);
                    playerBall.changeColor(Color.RED);
                    playerBall.setFingerPosition(playerBall.getPosition());
                }
                break;
        }
        return true;
    }

    public void newDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        backgroundPainter.setColor(Color.RED);
        blackPainter.setColor(Color.BLACK);
        canvas.drawRect(0,0,BOARD_WALLS_WIDTH,canvas.getHeight(), backgroundPainter);
        canvas.drawRect(canvas.getWidth(),0,canvas.getWidth()-BOARD_WALLS_WIDTH,canvas.getHeight(), backgroundPainter);
        canvas.drawRect(0,0,canvas.getWidth(),BOARD_WALLS_HEIGHT, backgroundPainter);
        canvas.drawRect(0,canvas.getHeight()-BOARD_WALLS_HEIGHT,canvas.getWidth(),canvas.getHeight(), backgroundPainter);
        if(playerBall.hasFingerPressed()) {
            Vector finger = playerBall.getFingerPosition();

            canvas.drawLine((float) playerBall.getPosition().getX(),
                    (float) playerBall.getPosition().getY(),
                    (float) playerBall.getFingerPosition().getX(),
                    (float) playerBall.getFingerPosition().getY(),
                    this.blackPainter);

            canvas.drawCircle((float)playerBall.getFingerPosition().getX(),(float) playerBall.getFingerPosition().getY(),10,blackPainter);
        }
        for (GameEntity b : entities) {
            b.paint(canvas);
        }
    }

    public void accelEntities(ArrayList<GameEntity> entities) {
        if(Utils.isBetween(r.nextInt(100),97,100)) {
            int i = r.nextInt(entities.size());
            MovableEntity me = null;
            if(entities.get(i) instanceof MovableEntity && (me = (MovableEntity) entities.get(i)).isMovable()) {
                float acceleration = Utils.clamp(r.nextFloat() * 2, 0.5f, 1f);
                me.setVelocity(me.getxVelocity() / acceleration + 2, me.getyVelocity() / acceleration + 2);
                me.setyVelocity(me.getyVelocity() / acceleration);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initGame() {
        playerBall.changeColor(Color.WHITE);
        playerInitialVector = new Vector(width*0.5+(BOARD_WALLS_WIDTH),height*0.20);
        playerBall.setPosition(playerInitialVector.getCopy());
        entities.add(playerBall);
        entities.addAll(Ball.getBiliardBalls(BOARD_WALLS_WIDTH,BOARD_WALLS_HEIGHT,width,height));
        for (int i = 0 ; i < 3 ; i++) {
            Hole holeRigth = new Hole();
            Hole holeLeft = new Hole();
            entities.add(holeRigth);
            entities.add(holeLeft);
            holeRigth.setInGame(true);
            double xL = BOARD_WALLS_WIDTH;
            double xR = width;
            double yL = ((int) (height / 2) * (i));
            double yR = ((int) (height / 2) * (i));
            if(i == 0) {
                yR += BOARD_WALLS_HEIGHT;
                yL += BOARD_WALLS_HEIGHT;
            }
            else if(i == 1) {
                xR += BOARD_WALLS_HEIGHT/3;
                xL -= BOARD_WALLS_HEIGHT/3;
            }
            holeRigth.setPosition(new Vector(xR,yR));

            holeLeft.setInGame(true);
            holeLeft.setPosition(new Vector(xL,yL));
        }
        isInitiated = true;
    }
}
