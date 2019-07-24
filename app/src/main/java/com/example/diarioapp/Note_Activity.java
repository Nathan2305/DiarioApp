package com.example.diarioapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note_Activity extends AppCompatActivity {
    FloatingActionButton fab;
    LinearLayout linearLayout;
    ArrayList<Note> lisNote;
    ArrayList<EditText> listEdt;
    TextView titleNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleNote = findViewById(R.id.titleNote);
        Intent intent = getIntent();
        String title_get = intent.getExtras().getString("title_note");
        if (!title_get.isEmpty()) {
            titleNote.setText(title_get);
        }
        lisNote = new ArrayList<>();
        linearLayout = findViewById(R.id.container_ll);
        listEdt = new ArrayList<>();
        fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View constraintLayout = layoutInflater.inflate(R.layout.each_note, null);
                CardView cardView = constraintLayout.findViewById(R.id.cardView);
                EditText editText = new EditText(getApplicationContext());
                editText.setBackground(null);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setPadding(40, 40, 40, 40);
                editText.requestFocus();
                listEdt.add(editText);
                cardView.addView(editText);
                linearLayout.addView(constraintLayout);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                for (int k = 0; k < listEdt.size(); k++) {
                    EditText editText = listEdt.get(k);
                    String notaTxt = editText.getText().toString();
                    if (!notaTxt.isEmpty()) {
                        Note note = new Note();
                        Date date = new Date();
                        String strDateFormat = "hh:mm:ss a";
                        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                        String formattedDate = dateFormat.format(date);
                        note.setText(notaTxt);
                        note.setPosition(k);
                        note.setDateText(formattedDate);
                        note.setTitleNote(titleNote.getText().toString());
                        insertNoteDB(note);
                    }
                }
        }
        return true;
    }

    private void insertNoteDB(Note note) {
        new TaskAddNote().execute(note);
    }

    private class TaskAddNote extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... my_note) {
            try {
                NoteDataBase.getInstance(getApplicationContext())
                        .getnoteDao().insertNotes(my_note);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Se guardaron notas", Toast.LENGTH_SHORT).show();
        }
    }
}
