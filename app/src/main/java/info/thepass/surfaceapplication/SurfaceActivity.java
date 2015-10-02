package info.thepass.surfaceapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SurfaceActivity extends Activity {
    private final static String TAG = "trak:Act";
    //    GL2JNIView mView;
    Context context;
    MySurfaceView myView;
    LinearLayout ll;
    Button button;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        context = this;
        setContentView(R.layout.main);
        ll = (LinearLayout) findViewById(R.id.ll);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isRunning) {
                    Log.d(TAG, "click stop");
                    isRunning = false;
                    myView.stopRunning();
                    button.setText("restart");
                } else {
                    Log.d(TAG, "click start");
                    isRunning = true;
                    myView = new MySurfaceView(context);
                    ll.addView(myView);
                    button.setText("stop");
                }
            }
        });
    }
}
