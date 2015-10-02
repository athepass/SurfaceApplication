package info.thepass.surfaceapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyThread extends Thread {
    public final static String TAG = "trak:myThread";
    boolean mRun;
    Canvas mcanvas;
    SurfaceHolder surfaceHolder;
    Context context;
    MySurfaceView myView;


    public MyThread(SurfaceHolder sHolder, Context ctx, MySurfaceView newView) {
        Log.d(TAG, "constructor");
        surfaceHolder = sHolder;
        context = ctx;
        mRun = false;
        myView = newView;
    }

    public void setRunning(boolean bRun) {
        Log.d(TAG, "setRunning " + bRun);
        mRun = bRun;
        if (mRun) {
            start();
        }
    }

    @Override
    public void run() {
        super.run();
        Log.d(TAG, "run");
        while (mRun) {
            mcanvas = surfaceHolder.lockCanvas();
            if (mcanvas != null) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                }
                myView.doDraw(mcanvas, "bla bla");
                surfaceHolder.unlockCanvasAndPost(mcanvas);
            }
        }
    }
}