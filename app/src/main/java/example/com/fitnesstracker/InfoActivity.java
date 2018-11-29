package example.com.fitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    Spinner spinner;
    EditText userName, weight, height;
    String newUserName, newGender, weightString, heightString;
    double newWeight, newHeight;
    public static final String TAG = InfoActivity.class.getSimpleName();
    //public static String Preference = "MYPREFS";
    //public static SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        userName = findViewById(R.id.username_txt);
        weight = findViewById(R.id.weight_txt);
        height = findViewById(R.id.height_txt);
        spinner = findViewById(R.id.gender_spinner);

        final String[] items = getResources().getStringArray(R.array.gender_list);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, items){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be used for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveInfo(View view) {

        newUserName = userName.getText().toString();
        newGender = spinner.getSelectedItem().toString();
        weightString = weight.getText().toString();
        heightString = height.getText().toString();

        if (weightString.length() > 0 &&
                heightString.length() > 0 &&
                newUserName.length() > 0 &&
                !(newGender.equals("Gender"))) {

            newWeight = Double.parseDouble(weight.getText().toString());
            newHeight = Double.parseDouble(height.getText().toString());
            /*

            preferences = getSharedPreferences(Preference, MODE_PRIVATE);
            //String ipAdrs=preferences.getString("key", "");
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("userNameTxt", newUserName);
            editor.putString("genderTxt", newGender);
            editor.putInt("weightTxt", newWeight);
            editor.putInt("heightTxt", newHeight);
            editor.commit();*/
            setDefaults("userNameTxt", newUserName, this);
            setDefaults("genderTxt", newGender, this);
            setDefaults("weightTxt", weightString, this);
            setDefaults("heightTxt", heightString, this);

            goToHomeActivity();
        } else {
            Toast.makeText(InfoActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    public void goToHomeActivity() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void clear(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
