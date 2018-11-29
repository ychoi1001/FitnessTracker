package example.com.fitnesstracker;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FitnessTrackerTableHandler {

    public static final String TABLE_FITNESSTRACKER = "fitnesstracker";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FITNESSTRACKER
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ACTIVITY + " text not null, "
            + COLUMN_DETAIL + " text not null,"
            + COLUMN_DATE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(FitnessTrackerTableHandler.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESSTRACKER);
        onCreate(database);
    }
}
