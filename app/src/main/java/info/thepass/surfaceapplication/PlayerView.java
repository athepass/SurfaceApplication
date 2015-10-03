package info.thepass.surfaceapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PlayerView extends SurfaceView
        implements SurfaceHolder.Callback {

    private final static String TAG = "trak:PlayerView";
    private final static String TAGM = "trak:PlayerThread";

    private final Paint paintImage = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private SurfaceHolder sh;
    private PlayerView pv;
    private Thread thread;
    private Metronome metronome;
    private Context ctx;
    private int counter = 0;

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "constructor");
        ctx = context;
        pv = this;
        sh = getHolder();
        sh.addCallback(this);

        paintImage.setColor(Color.BLUE);
        paintImage.setStyle(Paint.Style.FILL);

        paintText.setColor(Color.YELLOW);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(20);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        metronome = new Metronome();
        thread = new Thread(metronome);
        thread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void doPause() {
        Log.d(TAG, "doPause");
        metronome.onPause();
    }

    public void doResume() {
        Log.d(TAG, "doResume " + (metronome == null));
        if (metronome != null) {
            metronome.onResume();
        }
    }

    class Metronome implements Runnable {
        private Object mPauseLock;
        private boolean mPaused;
        private boolean mFinished;

        public Metronome() {
            Log.d(TAGM, "constructor");
            mPauseLock = new Object();
            mPaused = false;
            mFinished = false;
        }

        public void run() {
            Canvas canvas = sh.lockCanvas(null);
            canvas.drawColor(Color.BLACK);
            sh.unlockCanvasAndPost(canvas);

            while (!mFinished) {
                Log.d(TAGM, "run");
                Canvas c = null;
                try {
                    try {
                        wait(10);
                    } catch (Exception e) {
                    }
                    c = sh.lockCanvas(null);
                    synchronized (sh) {
                        counter++;
                        if (counter > 50)
                            counter = 0;
                        doDraw(c);
                    }
                } finally {
                    if (c != null) {
                        sh.unlockCanvasAndPost(c);
                    }
                }

                synchronized (mPauseLock) {
                    while (mPaused) {
                        Log.d(TAGM, "pauselock");
                        try {
                            mPauseLock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }

        /**
         * Call this on pause.
         */
        public void onPause() {
            Log.d(TAGM, "onPause");
            synchronized (mPauseLock) {
                mPaused = true;
            }
        }

        /**
         * Call this on resume.
         */
        public void onResume() {
            Log.d(TAGM, "onResume");
            synchronized (mPauseLock) {
                mPaused = false;
                counter = 0;
                mPauseLock.notify();
            }
        }

        private void doDraw(Canvas canvas) {
            Log.d(TAGM, "doDraw");
//            canvas.restore();
            canvas.drawColor(Color.BLACK);
            canvas.drawCircle(20 + counter * 10, 20, 50, paintImage);
            canvas.drawText("counter=" + counter, 20, 20, paintText);
        }

    }
}