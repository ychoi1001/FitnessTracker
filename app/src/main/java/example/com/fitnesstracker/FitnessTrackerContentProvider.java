package example.com.fitnesstracker;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;

public class FitnessTrackerContentProvider extends ContentProvider {

    private DatabaseHandler database;

    private static final int ACTIVITIES = 10;
    private static final int ACTIVITY_ID = 20;

    private static final String AUTHORITY = "example.com.fitnesstracker.FitnessTrackerContentProvider";

    private static final String BASE_PATH = "activities";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/activity";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, ACTIVITIES);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ACTIVITY_ID);
    }

    public static final String TAG = FitnessTrackerContentProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        database = new DatabaseHandler(getContext());
        Log.i(TAG,TAG + " is created - Yeonsil Choi 147940183");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        checkColumns(projection);

        queryBuilder.setTables(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER);

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case ACTIVITIES:
                break;
            case ACTIVITY_ID:
                queryBuilder.appendWhere(FitnessTrackerTableHandler.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                FitnessTrackerTableHandler.COLUMN_ID,
                FitnessTrackerTableHandler.COLUMN_ACTIVITY,
                FitnessTrackerTableHandler.COLUMN_DETAIL,
                FitnessTrackerTableHandler.COLUMN_DATE
        };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)){
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case ACTIVITIES:
                id = sqlDB.insert(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown RI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case ACTIVITIES:
                rowsDeleted = sqlDB.delete(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, selection, selectionArgs);
                break;
            case ACTIVITY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, FitnessTrackerTableHandler.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, FitnessTrackerTableHandler.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowUpdated = 0;

        switch (uriType) {
            case ACTIVITIES:
                rowUpdated = sqlDB.update(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, values, selection, selectionArgs);
                break;
            case ACTIVITY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowUpdated = sqlDB.update(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, values, FitnessTrackerTableHandler.COLUMN_ID + "=" + id, null);
                } else {
                    rowUpdated = sqlDB.update(FitnessTrackerTableHandler.TABLE_FITNESSTRACKER, values, FitnessTrackerTableHandler.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }
}
