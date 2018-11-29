package example.com.fitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    String name, genderSt, weightSt, heightSt;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Clear SharedPreferences
                //InfoActivity.clear(MainActivity.this);


                name = InfoActivity.getDefaults("userNameTxt",MainActivity.this);
                genderSt = InfoActivity.getDefaults("genderTxt", MainActivity.this);
                weightSt = InfoActivity.getDefaults("weightTxt", MainActivity.this);
                heightSt = InfoActivity.getDefaults("heightTxt", MainActivity.this);

                if (TextUtils.isEmpty(name) | TextUtils.isEmpty(genderSt) | TextUtils.isEmpty(weightSt) | TextUtils.isEmpty(heightSt)) {
                    Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(infoIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);

    }

}
