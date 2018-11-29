package example.com.fitnesstracker;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class RoutineFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    ListView listView;
    EditText activityEt, detailEt;
    TextView currentDate, currentDateInRoutineFrag;
    Integer counter;
    String counterString;
    CheckedTextView ctv;
    Uri routineUri;
    View view;
    private final LocalTime ONE_MINUTE_BEFORE_MIDNIGHT = LocalTime.of(23, 59, 0);
    private final LocalTime ONE_MINUTE_AFTER_MIDNIGHT = LocalTime.of(0, 1, 0);
    public static final String TAG = RoutineFragment.class.getSimpleName();

    public RoutineFragment() {
       /* try {
            counterString2 = InfoActivity.getDefaults("counter", getActivity());
            Log.i("t", counterString2);
        }
        catch (Exception E ){
            System.out.println(E);
            System.out.println(E.getMessage());
        } */
        /*try {
            if (InfoActivity.getDefaults("counter", getActivity()) == null) {
                counter = 0;
            } else {
                Integer.parseInt(InfoActivity.getDefaults("counter", getActivity()));
            }
        }

        catch(Exception E){
            Log.i("test", InfoActivity.getDefaults("counter", getActivity()) );
            counter = 0;
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");

        activityEt = view.findViewById(R.id.activity_title);
        detailEt = view.findViewById(R.id.activity_detail);
        currentDate = view.findViewById(R.id.current_date);

        currentDateInRoutineFrag = view.findViewById(R.id.currentdate);
        currentDateInRoutineFrag.setText(currentDateTime());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoutineDetailFragment()).addToBackStack(null).commit();

            }
        });

        listView = view.findViewById(R.id.list_view);
        listView.setEmptyView(view.findViewById(R.id.empty_list));

        registerForContextMenu(listView);

        if (isAtMidnight(LocalTime.now()) == true) {
            listView.setAdapter(null);
            int i = 0;
            String st = String.valueOf(i);
            InfoActivity.setDefaults("counter", st, getActivity());
        } else {
            fillData();
        }
       // }
        //listView.setAdapter(adapter);
        //max = listView.getCount();


        //helloText = findViewById(R.id.hello_user);
        //helloText.setText("Hello, " + username);

        try {
            if (InfoActivity.getDefaults("counter", getActivity()) == null) {
                counter = 0;
            } else {
                counter = Integer.parseInt(InfoActivity.getDefaults("counter", getActivity()));
            }
        } catch(Exception E){
            Log.i("test", InfoActivity.getDefaults("counter", getActivity()) );
            counter = 0;
        }
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {FitnessTrackerTableHandler.COLUMN_ID, FitnessTrackerTableHandler.COLUMN_ACTIVITY};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), FitnessTrackerContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        //max = adapter.getCount();
        //String maxString = String.valueOf(max);
        //InfoActivity.setDefaults("max", maxString, getActivity());
        //Log.isLoggable("a",i);
        //int count = listView.getAdapter().getCount();
        //Log.isLoggable("b",count);
        //checkBox = listView.findViewById(R.id.check_box);
        //ctv = listView.findViewById(R.id.label);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { FitnessTrackerTableHandler.COLUMN_ACTIVITY };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label };

        android.support.v4.app.LoaderManager.getInstance(this).initLoader(0, null, this);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.routine_row, null, from, to, 0);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //Bundle arguments = new Bundle();
                //routineUri = Uri.parse(FitnessTrackerContentProvider.CONTENT_URI + "/" + id);
                //arguments.putParcelable(String.valueOf(Uri.parse(FitnessTrackerContentProvider.CONTENT_ITEM_TYPE)), routineUri);
                //RoutineDetailFragment fr = new RoutineDetailFragment();
                //fr.setArguments(arguments);


        ctv = view.findViewById(R.id.label);

        if(!(ctv.isChecked())){
            ctv.setChecked(true);
            if (counter == null) {
                counter = 0;
            }
            counter++;
            counterString = String.valueOf(counter);
            InfoActivity.setDefaults("counter", counterString, getActivity());
            final Uri uri = Uri.parse(FitnessTrackerContentProvider.CONTENT_URI + "/" + id);
            getActivity().getContentResolver().delete(uri, null, null);
            fillData();
            Toast.makeText(getActivity(), "Successfully Completed! Great Work!!", Toast.LENGTH_LONG).show();

                   // boolean uncheck = false;
                   // String unchecked = String.valueOf(uncheck);
                   // InfoActivity.setDefaults("check_state", unchecked, getActivity());
                   // ctv.setChecked(Boolean.parseBoolean(unchecked));
            }
                //int i = adapter.getCount();
                //Log.isLoggable("a",i);

                //int count = listView.getAdapter().getCount();
                //Log.isLoggable("b",count);

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        long id = adapter.getItemId(position);
        switch(item.getItemId()) {
            case R.id.edit:
                Bundle arguments = new Bundle();
                routineUri = Uri.parse(FitnessTrackerContentProvider.CONTENT_URI + "/" + id);
                arguments.putParcelable(String.valueOf(Uri.parse(FitnessTrackerContentProvider.CONTENT_ITEM_TYPE)), routineUri);
                RoutineDetailFragment fr = new RoutineDetailFragment();
                fr.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack(null).commit();
                break;
            case R.id.delete:
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Uri uri = Uri.parse(FitnessTrackerContentProvider.CONTENT_URI + "/" + info.id);
                getActivity().getContentResolver().delete(uri, null, null);
                fillData();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_menu, menu);
    }

    public String currentDateTime() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    //private LocalDate lastCheck = null;

    /*public void isNewDay() {
        LocalDate today = LocalDate.now();
        boolean ret = lastCheck == null || today.isAfter(lastCheck);
        lastCheck = today;

        if (ret == false) {
            listView.setAdapter(null);
        } else {
            fillData();
        }

        LocalTime l = LocalTime.now();

        System.out.println(l.isAfter(LocalTime.MIDNIGHT));
    }*/

    public boolean isAtMidnight(LocalTime time){
        return time.isAfter(ONE_MINUTE_BEFORE_MIDNIGHT) || time.isBefore(ONE_MINUTE_AFTER_MIDNIGHT);
    }
}
