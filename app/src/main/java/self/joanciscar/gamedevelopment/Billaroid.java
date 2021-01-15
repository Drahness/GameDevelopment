package self.joanciscar.gamedevelopment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
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
    Paint painter = new Paint();
    Paint blackPainter = new Paint();
    private int height;
    private int width;
    private static final float BOARD_WALLS_HEIGHT = 75;
    private static final float BOARD_WALLS_WIDTH = 75;
    private final Random r = new Random();
    private static boolean isInitiated = false;
    Ball playerBall = new Ball(25);

    private Vector actionMoveFinal = null;

    ArrayList<GameEntity> entities = new ArrayList<>();

    public Billaroid(Context context) {
        super(context);
        this.getHolder().addCallback(this);
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
        painter.setColor(Color.BLUE);
        painter.setStyle(Paint.Style.FILL);
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
                        playerBall.changeColor(Color.WHITE);
                        playerBall.setPosition(new Vector(width*0.5+(BOARD_WALLS_WIDTH),height*0.80));
                        entities.add(playerBall);
                        if(entities.size() < 10) { // TODO esto no se va a ejecutar de buena manera
                            // todo del palo que solo añadira una bola, pero es para testeos.
                            Ball b = new Ball(25);
                            entities.add(b);
                            b.changeColor(Color.rgb(r.nextFloat(),r.nextFloat(),r.nextFloat()));
                            b.setPosition(new Vector(r.nextInt(width),r.nextInt(height)));
                            b.setxVelocity(r.nextDouble()*10);
                            b.setyVelocity(r.nextDouble()*10);
                        }
                        isInitiated = true;
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
                            holeRigth.setPosition(new Vector(xR,yR));

                            holeLeft.setInGame(true);
                            holeLeft.setPosition(new Vector(xL,yL));
                        }
                    }
                    for (GameEntity b : entities) {
                        if(b.isMovable()) {
                            MovableEntity movableEntity = (MovableEntity) b;
                            if(movableEntity.isMoving()) {
                                if (b.hasPosition()) {
                                    for (GameEntity gameEntity : entities) {
                                        if (gameEntity.hasPosition() && gameEntity != b) {
                                            b.processDump(gameEntity, height, width, BOARD_WALLS_HEIGHT, BOARD_WALLS_WIDTH);
                                        }
                                    }
                                    movableEntity.move(height, width, BOARD_WALLS_HEIGHT, BOARD_WALLS_WIDTH);
                                }
                            }
                        }
                    }
                    // for testing accelEntities(entities);
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
                    // Repainting the canvas
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
                if(this.actionMoveFinal != null) {
                    double distance = playerBall.getPosition().distance(this.actionMoveFinal);
                    double powerOfThrow;
                    if (distance > 100) {
                        powerOfThrow = 20;
                    } else {
                        powerOfThrow = distance / 5;
                    }
                    playerBall.setVelocity(Vector.newVector(5, playerBall.getPosition(), actionMoveFinal));
                    actionMoveFinal = null;
                }
                playerBall.changeColor(Color.WHITE);
            case MotionEvent.ACTION_MOVE: // mantener pulsado
                System.out.println();
                if(event.getHistorySize() > 0) {
                    actionMoveFinal = new Vector(event.getHistoricalX(event.getHistorySize() - 1), event.getHistoricalY(event.getHistorySize() - 1));
                }
                break;
            case MotionEvent.ACTION_DOWN:
                double minX, maxX;
                double minY, maxY;
                double x = event.getX();
                double y = event.getY();
                maxX = playerBall.getPosition().getX() + playerBall.getRadius();
                minX =  playerBall.getPosition().getX() - playerBall.getRadius();
                maxY = playerBall.getPosition().getY() + playerBall.getRadius();
                minY = playerBall.getPosition().getY() - playerBall.getRadius();
                if(Utils.isBetween(x,minX,maxX) && Utils.isBetween(y,minY,maxY)) {
                    playerBall.changeColor(Color.RED);
                }
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                System.out.println();
                break;

        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        painter.setColor(Color.RED);
        blackPainter.setColor(Color.BLACK);
        canvas.drawRect(0,0,BOARD_WALLS_WIDTH,canvas.getHeight(),painter);
        canvas.drawRect(canvas.getWidth(),0,canvas.getWidth()-BOARD_WALLS_WIDTH,canvas.getHeight(),painter);
        canvas.drawRect(0,0,canvas.getWidth(),BOARD_WALLS_HEIGHT,painter);
        canvas.drawRect(0,canvas.getHeight()-BOARD_WALLS_HEIGHT,canvas.getWidth(),canvas.getHeight(),painter);
        if(actionMoveFinal != null) {
            canvas.drawLine((float) playerBall.getPosition().getX(),
                            (float) playerBall.getPosition().getY(),
                            (float) actionMoveFinal.getX(),
                            (float) actionMoveFinal.getX(),
                            this.blackPainter);
        }
        entities.forEach(b -> b.paint(canvas));
        //canvas.drawCircle(current_x, current_y, radius, painter);
    }

    public void accelEntities(ArrayList<GameEntity> entities) {
        if(Utils.isBetween(r.nextInt(100),97,100)) {
            MovableEntity me = null;
            if((me = (MovableEntity) entities.get(r.nextInt(entities.size()))).isMovable()) {
                double acceleration = Utils.clamp(r.nextDouble() * 2, 0.5, 1);
                me.setVelocity(me.getxVelocity() / acceleration + 2, me.getyVelocity() / acceleration + 2);
                me.setyVelocity(me.getyVelocity() / acceleration);
            }
        }
    }
}
