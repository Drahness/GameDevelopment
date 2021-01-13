package self.joanciscar.gamedevelopment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Billaroid extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread billaroidThread;
    Paint painter = new Paint();

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
                if(historySize >= 2 ) {
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

    float vel = 0;
    float current_x = -1;
    float current_y = -1;
    final float RADIUS = 20f;
    float radius = RADIUS;
    public void newDraw(Canvas canvas) {
        float width = canvas.getWidth(),  high = canvas.getHeight();
        if(current_x == -1) {
            current_x = width / 2;
        }
        if(current_y == -1) {
            current_y = high / 2;
        }
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(current_x, current_y, radius, painter);
    }
}
