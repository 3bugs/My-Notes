package com.promlert.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Promlert on 1/16/2016.
 */
public class NotesDb {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "notes";
    private static final String COL_ID = "_id";
    private static final String COL_NOTE = "note";
    private static final String COL_IMPORTANT = "important";

    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NOTE + " TEXT, "
                    + COL_IMPORTANT + " INTEGER)";

    private static DatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;

    public NotesDb(Context context) {
        if (mHelper == null) {
            mHelper = new DatabaseHelper(context);
        }
        mDatabase = mHelper.getWritableDatabase();
    }

    public ArrayList<Note> getAllNotes() {

        ArrayList<Note> noteArrayList = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                com.promlert.mynotes.DatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            int important = cursor.getInt(cursor.getColumnIndex("important"));

            Note n = null;
            if (important == 0) {
                n = new Note(id, note, false);
            } else if (important == 1) {
                n = new Note(id, note, true);
            }

            noteArrayList.add(n);
            Log.i("MainActivity", "note: " + note + ", important: " + important);
        }

        return noteArrayList;
    }

    public long insertNote(Note note) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE, note.text);

        if (note.isImportant) {
            cv.put(COL_IMPORTANT, "1");
        } else {
            cv.put(COL_IMPORTANT, "0");
        }

        long result = mDatabase.insert(TABLE_NAME, null, cv);
        return result;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

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


}
