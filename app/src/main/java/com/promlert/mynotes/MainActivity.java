package com.promlert.mynotes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;
    private ArrayAdapter<Note> mAdapter;

    private ArrayList<Note> noteArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DatabaseHelper(this);
        mDatabase = mHelper.getWritableDatabase();

        ListView list = (ListView) findViewById(R.id.listView);

        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,
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

        mAdapter = new ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_1,
                noteArrayList
        );

        list.setAdapter(mAdapter);


    }
}
