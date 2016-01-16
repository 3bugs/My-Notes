package com.promlert.mynotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Note> mAdapter;
    private NotesDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listView);

        db = new NotesDb(this);
        ArrayList<Note> noteArrayList = db.getAllNotes();

        mAdapter = new ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_1,
                noteArrayList
        );

        list.setAdapter(mAdapter);
    }
}
