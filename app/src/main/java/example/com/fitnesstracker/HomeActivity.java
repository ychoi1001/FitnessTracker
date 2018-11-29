package example.com.fitnesstracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText weightText, heightText;
    double bmi, weight, height, squaredHeight;
    private ImageView imgView, profile;
    String bmiResult ,username, weightBmi, heightBmi, genderString, imageUri, taskNum;
    TextView helloText, taskNumText;
    BMIDialog alertDialog;
    public static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");
        // Get the Intent that started this activity and extract the string

        username = InfoActivity.getDefaults("userNameTxt",HomeActivity.this);
        helloText = findViewById(R.id.hello_user);
        helloText.setText("Hello, " + username);

        taskNum = InfoActivity.getDefaults("counter", HomeActivity.this);
        taskNumText = findViewById(R.id.task_num);
        taskNumText.setText("You completed " + taskNum + " of tasks!");

        genderString = InfoActivity.getDefaults("genderTxt", HomeActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        navUsername.setText(username);
        profile = headerView.findViewById(R.id.imageView);


        if (genderString.equals("Female")) {
            imageUri = "@drawable/ic_female2";
            int imageResource = getResources().getIdentifier(imageUri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            profile.setImageDrawable(res);
        } else {
            imageUri = "@drawable/ic_male2";
            int imageResource = getResources().getIdentifier(imageUri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            profile.setImageDrawable(res);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent in = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(in);
                overridePendingTransition(0,0); // for no animation
                break;
            case R.id.nav_bmi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BMIFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_routine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoutineFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactUsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_about:
                Intent i = new Intent(HomeActivity.this, About.class);
                startActivity(i);
                break;
            case R.id.nav_exit:
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateBMI(View view){
        weightText = findViewById(R.id.weight_et);
        heightText = findViewById(R.id.height_et);

        weightBmi = weightText.getText().toString();
        heightBmi = heightText.getText().toString();

        if (weightBmi.length() > 0 && heightBmi.length() > 0) {
            weight = Double.parseDouble(weightText.getText().toString());
            height = Double.parseDouble(heightText.getText().toString());
            squaredHeight = Math.pow(height, 2);

            bmi = weight / squaredHeight;
            bmiResult = String.format("%.2f", bmi);

            final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.animation_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            imgView = (ImageView) dialog.findViewById(R.id.picture);
            imgView.setBackgroundResource(R.drawable.frame_animation_old2);
            final AnimationDrawable animcon = (AnimationDrawable) imgView.getBackground();
            dialog.setCancelable(false);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    animcon.start();
                }
            });
            dialog.show();
            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    dialog.dismiss();
                    // android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    // BMIDialog alertDialog = BMIDialog.newInstance("Your Result is...", bmiResult);
                    // alertDialog.show(fm, "fragment_alert");
                    showBmi();
                    t.cancel();
                }
            }, 2000);
            //refresh();
        } else {
            Toast.makeText(HomeActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void showBmi(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        alertDialog = BMIDialog.newInstance("Your BMI is...", bmiResult, weightBmi, heightBmi);
        alertDialog.show(fm, "fragment_alert");
    }

    /*
    public void refresh() {
        weightEt = findViewById(R.id.weight_et);
        weightEt.setText("");
        heightEt = findViewById(R.id.height_et);
        heightEt.setText("");

        prevHeight = InfoActivity.getDefaults("heightTxt", this);
        prevHeightTv = findViewById(R.id.prev_height);
        prevHeightTv.setText(prevHeight);

        prevWeight = InfoActivity.getDefaults("weightTxt", this);
        prevWeightTv = findViewById(R.id.prev_weight);
        prevWeightTv.setText(prevWeight);
    }
    */
}
