package info.thepass.surfaceapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SurfaceActivity extends Activity {
    private final static String TAG = "trak:Act";
    //    GL2JNIView mView;
    Context context;
    PlayerView surfaceView;
    Button buttonStart;
    Button buttonStop;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        context = this;
        setContentView(R.layout.main);
        surfaceView = (PlayerView) findViewById(R.id.sv);

        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doStop();
            }
        });
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doStart();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        surfaceView.doPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        surfaceView.doResume();
    }

    private void doStop() {
        Log.d(TAG, "click stop");
        surfaceView.doPause();
    }

    private void doStart() {
        Log.d(TAG, "click start");
        surfaceView.doResume();
    }
}
