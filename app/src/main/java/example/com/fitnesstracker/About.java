package example.com.fitnesstracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class About extends Activity {

    public static final String TAG = About.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");
    }
}
