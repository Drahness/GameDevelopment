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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Billaroid extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread billaroidThread;
    Paint painter = new Paint();
    Ball firstBall = new Ball(25);
    Ball secondBall = new Ball(25);

    ArrayList<Ball> ballsInGame = new ArrayList<>();

    public Billaroid(Context context) {
        super(context);
        this.getHolder().addCallback(this);
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

        @Override
        public void run() {
            super.run();
            while (!stop) {
                Canvas canvas = null;
                // 5.- Calculate conditions, new movement, interactions …
                try {
                    canvas = holder.lockCanvas(null);
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
                float minX, maxX;
                float minY, maxY;
                float x = event.getX();
                float y = event.getY();
                maxX = current_x + radius + RADIUS;
                minX = current_x - radius - RADIUS;
                maxY = current_y + radius + RADIUS;
                minY = current_y - radius - RADIUS;
                if(Utils.isBetween(x,minX,maxX) && Utils.isBetween(y,minY,maxY)) {
                    radius = RADIUS + (((float) (System.currentTimeMillis() % 1000)) / 20);
                }
                break;
            case MotionEvent.ACTION_MOVE: // mantener pulsado
                System.out.println();
                break;
            case MotionEvent.ACTION_DOWN:
                int historySize = event.getHistorySize();
                if(historySize >= 2) {
                    Point inicio = new Point(event.getHistoricalX(0),event.getHistoricalY(0));
                    Point fin = new Point(event.getHistoricalX(event.getHistorySize() - 1),event.getHistoricalY(event.getHistorySize() - 1));
                }
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                System.out.println();
                break;

        }
        return true;
    }
    Random r = new Random();

    float current_x = -1;
    float current_y = -1;
    final float RADIUS = 20f;
    float radius = RADIUS;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void newDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if(ballsInGame.size() < 10) {
            Ball b = new Ball(25);
            ballsInGame.add(b);
            b.changeColor(Color.rgb(r.nextFloat(),r.nextFloat(),r.nextFloat()));
            b.setPosition(new Point(r.nextInt(canvas.getWidth()),r.nextInt(canvas.getHeight())));
            b.setxVelocity(r.nextDouble()*10);
            b.setyVelocity(r.nextDouble()*10);
        }
        for (Ball b : ballsInGame) {
            if(b.hasPosition()) {
                for (Ball ballDump : ballsInGame) {
                    if (ballDump != b) {
                        b.processDump(ballDump,canvas.getHeight(),canvas.getWidth());
                    }
                }
                b.move(canvas.getHeight(),canvas.getWidth());
                b.paint(canvas);
            }
        }
        if(Utils.isBetween(r.nextInt(100),97,100)) {
            Ball accelerated = ballsInGame.get(r.nextInt(ballsInGame.size()));
            double acceleration = Utils.clamp(r.nextDouble()*2, 0.5,1);
            accelerated.setxVelocity(accelerated.getxVelocity()/acceleration);
            accelerated.setyVelocity(accelerated.getyVelocity()/acceleration);
        }
        //canvas.drawCircle(current_x, current_y, radius, painter);
    }
}
