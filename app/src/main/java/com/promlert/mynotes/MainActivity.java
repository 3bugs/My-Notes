package com.promlert.mynotes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private NotesDb mDb;
    private ListView mListView;
    private ArrayList<Note> mNoteArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = new NotesDb(this);

        mListView = (ListView) findViewById(R.id.listView);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText noteEditText = (EditText) findViewById(R.id.note_edit_text);
                String inputText = noteEditText.getText().toString();

                CheckBox importantCheckBox = (CheckBox) findViewById(R.id.important_check_box);
                boolean isImportant = importantCheckBox.isChecked();

                mDb.insertNote(inputText, isImportant);
                reloadData();
            }
        });

        mAdapter = new MyAdapter(
                MainActivity.this,
                R.layout.list_item,
                mNoteArrayList
        );
        mListView.setAdapter(mAdapter);

        reloadData();
    }

    private void reloadData() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Note> noteArrayList = mDb.getAllNotes();
                mNoteArrayList.clear();
                mNoteArrayList.addAll(noteArrayList);

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        t.start();
    }

    private static class MyAdapter extends ArrayAdapter {

        private Context context;
        private int layoutResId;
        private ArrayList<Note> noteArrayList;

        public MyAdapter(Context context, int resource, ArrayList<Note> noteArrayList) {
            super(context, resource, noteArrayList);

            this.context = context;
            this.layoutResId = resource;
            this.noteArrayList = noteArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(context, layoutResId, null);

            LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
            View importantView = view.findViewById(R.id.important_view);

            TextView noteTextView = (TextView) view.findViewById(R.id.note_text_view);
            noteTextView.setText(noteArrayList.get(position).text);

            //TextView importantTextView = (TextView) view.findViewById(R.id.important_text_view);
            //importantTextView.setText(String.valueOf(noteArrayList.get(position).isImportant));

            boolean isImportant = noteArrayList.get(position).isImportant;
            if (isImportant) {
                importantView.setBackgroundResource(R.color.colorAccent);
            } else {
                importantView.setBackgroundColor(Color.TRANSPARENT);
            }

            View bottomLine = view.findViewById(R.id.bottom_line);
            if (position == noteArrayList.size() - 1) {
                bottomLine.setVisibility(View.VISIBLE);
            } else {
                bottomLine.setVisibility(View.GONE);
            }

            return view;
        }
    }
}
