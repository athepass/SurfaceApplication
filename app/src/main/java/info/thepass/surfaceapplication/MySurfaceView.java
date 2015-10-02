package info.thepass.surfaceapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final static String TAG = "trak:MySurfaceView";
    public MyThread mythread;
    Context context;
    boolean isRunning;
    Paint paintCircle;
    Paint paintBackground;
    Paint paintText;
    int circleX;
    int deltaX = 0;
    int circleY;
    int radius;
    int counter = 0;
    SurfaceHolder holder;

    public MySurfaceView(Context context) {
        super(context);
        Log.d(TAG,"constructor");
        this.context = context;

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        paintBackground = new Paint();
        paintBackground.setStyle(Paint.Style.FILL);
        paintBackground.setColor(Color.WHITE);

        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.YELLOW);

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.RED);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        mythread = new MyThread(holder, context, this);
        mythread.setRunning(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mythread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                mythread.join();
                retry = false;
            } catch (Exception e) {
                Log.v("Exception Occured", e.getMessage());
            }
        }
    }

    void doDraw(Canvas canvas, String info) {
        Log.d(TAG, "doDraw");
        counter++;
        canvas.drawColor(Color.GREEN);
        canvas.drawCircle(30 + counter * 3, 30, 20, paintCircle);
        canvas.drawText(counter + ": " + info, 10, 10, paintText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");

        circleX = (int) getWidth() / 2;
        circleY = (int) getHeight() / 2;
        radius = circleX / 10;

        canvas.drawPaint(paintBackground);
        canvas.drawCircle(circleX + deltaX, circleY, radius, paintCircle);

    }

    public void stopRunning() {
        mythread.setRunning(false);
    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouch");
        if (deltaX < circleX) {
            deltaX += 5;
        } else {
            deltaX -= 5;
        }
        invalidate();// call invalidate to refresh the draw
        return true;
    }
}
