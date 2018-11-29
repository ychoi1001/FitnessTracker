package example.com.fitnesstracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;
// ...

public class BMIDialog extends DialogFragment {

    String updatedWeight, updatedHeight;
    View view;
    public static final String TAG = BMIDialog.class.getSimpleName();

    public BMIDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static BMIDialog newInstance(String title, String result, String weight, String height) {
        BMIDialog frag = new BMIDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("result", result);
        args.putString("weight_string", weight);
        args.putString("height_string", height);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        String title = getArguments().getString("title");
        final String result = getArguments().getString("result");
        final String weight = getArguments().getString("weight_string");
        final String height = getArguments().getString("height_string");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(result);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InfoActivity.setDefaults("weightTxt", weight, getActivity().getApplicationContext());
                InfoActivity.setDefaults("heightTxt", height, getActivity().getApplicationContext());

                updatedWeight = InfoActivity.getDefaults("weightTxt", getActivity().getApplicationContext());
                updatedHeight = InfoActivity.getDefaults("heightTxt", getActivity().getApplicationContext());

                Toast.makeText(getActivity().getApplicationContext(), "Successfully Updated!", Toast.LENGTH_LONG).show();
                //android.support.v4.app.FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BMIFragment());
               //getActivity().getSupportFragmentManager().beginTransaction().remove(BMIFragment.class).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BMIFragment()).addToBackStack(null).commit();
                getActivity().onBackPressed();
            }
        });

        return alertDialogBuilder.create();
    }

}