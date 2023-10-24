package com.example.tasktrackerapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class TaskContentProvider extends ContentProvider {

    public final static String DBNAME = "TaskDatabase";
    public final static String TABLE_NAMESTABLE = "Tasks";
    public final static String COLUMN_FIRSTNAME = "description";
    public final static String COLUMN_LASTNAME = "owner";

    public static final String AUTHORITY = "com.belmont.app.TaskCP";
    public static final Uri CONTENT_URI = Uri.parse(
            "content://" + AUTHORITY +"/" + TABLE_NAMESTABLE);

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAMESTABLE +  // Table's name
            "(" +               // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_FIRSTNAME +
            " TEXT," +
            COLUMN_LASTNAME +
            " TEXT)";
    private MainDatabaseHelper mOpenHelper;

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }
        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

    public TaskContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().
                delete("Tasks", selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String description = values.getAsString("description");
        String owner = values.getAsString("owner");
        // Note: should also check for null
        if(description.trim().length() > 0 && owner.trim().length() > 0 ) {
            long id = mOpenHelper.getWritableDatabase().insert("Tasks", null, values);
            uri = Uri.withAppendedPath(CONTENT_URI, "" + id);
        }else{
            uri = null;
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}