package com.promlert.mynotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText noteEditText = (EditText) findViewById(R.id.note_edit_text);
                String inputText = noteEditText.getText().toString();
                CheckBox importantCheckBox = (CheckBox) findViewById(R.id.important_check_box);
                boolean isImportant = importantCheckBox.isChecked();

                Note n = new Note(
                        -1,
                        inputText,
                        isImportant
                );

                db.insertNote(n);
            }
        });


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
