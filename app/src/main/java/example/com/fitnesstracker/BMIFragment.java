package example.com.fitnesstracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class BMIFragment extends Fragment {
    String prevWeight, prevHeight;
    Context thisContext;
    TextView prevWeightTxt, prevHeightTxt;
    View view;
    EditText weightEt;
    public static final String TAG = BMIFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bmi, container, false);
        thisContext = container.getContext();

        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        prevWeight = InfoActivity.getDefaults("weightTxt", thisContext);
        prevWeightTxt = view.findViewById(R.id.prev_weight);
        prevWeightTxt.setText(prevWeight);

        prevHeight = InfoActivity.getDefaults("heightTxt", thisContext);
        prevHeightTxt = view.findViewById(R.id.prev_height);
        prevHeightTxt.setText(prevHeight);

        weightEt = view.findViewById(R.id.weight_et);

        return view;
    }

}
