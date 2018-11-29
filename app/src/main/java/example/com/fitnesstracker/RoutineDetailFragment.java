package example.com.fitnesstracker;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RoutineDetailFragment extends Fragment {

    EditText activityEt, detailEt;
    TextView currentDate;
    String currentDateString;
    Button confirmBtn;
    private Uri activityUri;
    public static final String TAG = RoutineDetailFragment.class.getSimpleName();

    public RoutineDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_r_detail, container, false);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        activityEt = view.findViewById(R.id.activity_title);
        detailEt = view.findViewById(R.id.activity_detail);
        currentDate = view.findViewById(R.id.current_date);
        currentDateString = currentDateTime();
        currentDate.setText(currentDateString);
        confirmBtn = view.findViewById(R.id.confirm_btn);

        //Bundle extras = getActivity().getIntent().getExtras();
        Bundle extras = this.getArguments();

        activityUri = (bundle == null) ? null : (Uri) bundle.getParcelable(FitnessTrackerContentProvider.CONTENT_ITEM_TYPE);

        if (extras != null) {
            activityUri = extras.getParcelable(FitnessTrackerContentProvider.CONTENT_ITEM_TYPE);
            fillData(activityUri);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(activityEt.getText().toString()) && TextUtils.isEmpty(detailEt.getText().toString())) {
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                } else {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().onBackPressed();
                }
            }
        });

        return view;
    }

    private void fillData(Uri uri) {
        String[] projection = {FitnessTrackerTableHandler.COLUMN_ACTIVITY,
        FitnessTrackerTableHandler.COLUMN_DETAIL,
        FitnessTrackerTableHandler.COLUMN_DATE};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            activityEt.setText(cursor.getString(cursor.getColumnIndexOrThrow(FitnessTrackerTableHandler.COLUMN_ACTIVITY)));
            detailEt.setText(cursor.getString(cursor.getColumnIndexOrThrow(FitnessTrackerTableHandler.COLUMN_DETAIL)));
            currentDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(FitnessTrackerTableHandler.COLUMN_DATE)));
            cursor.close();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(FitnessTrackerContentProvider.CONTENT_ITEM_TYPE, activityUri);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String activityName = activityEt.getText().toString();
        String activityDetail = detailEt.getText().toString();
        String activityDate = currentDate.getText().toString();

        if (activityName.length() == 0 && activityDetail.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(FitnessTrackerTableHandler.COLUMN_ACTIVITY, activityName);
        values.put(FitnessTrackerTableHandler.COLUMN_DETAIL, activityDetail);
        values.put(FitnessTrackerTableHandler.COLUMN_DATE, activityDate);

        if (activityUri == null) {
            activityUri = getActivity().getContentResolver().insert(FitnessTrackerContentProvider.CONTENT_URI, values);
        } else {
            getActivity().getContentResolver().update(activityUri, values, null, null);
        }
    }

    public String currentDateTime() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }
}
