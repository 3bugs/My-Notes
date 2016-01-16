package com.promlert.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Promlert on 1/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "notes";
    public static final String COL_ID = "_id";
    public static final String COL_NOTE = "note";
    public static final String COL_IMPORTANT = "important";

    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NOTE + " TEXT, "
                    + COL_IMPORTANT + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);

        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE, "Hello");
        cv.put(COL_IMPORTANT, 1);
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_NOTE, "abc");
        cv.put(COL_IMPORTANT, 0);
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_NOTE, "def");
        cv.put(COL_IMPORTANT, 0);
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_NOTE, "ghi");
        cv.put(COL_IMPORTANT, 1);
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
