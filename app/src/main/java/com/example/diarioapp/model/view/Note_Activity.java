package com.example.diarioapp.model.view;

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

import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Note;
import com.example.diarioapp.entities.pojo.Paragraph;
import com.example.diarioapp.model.db.AppDataBase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Note_Activity extends AppCompatActivity {
    FloatingActionButton fab;
    LinearLayout linearLayout;
    ArrayList<EditText> listEdt;
    TextView titleNote;
    Note auxNote;

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
                insertNoteDB();
        }
        return true;
    }

    private void insertNoteDB() {
        new TaskAddNote().execute();
    }

    private class TaskAddNote extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                String formattedDate = dateFormat.format(date);
                Note note = new Note();
                note.setTitle(titleNote.getText().toString());
                note.setDateTitlenote(formattedDate);
                long resul=AppDataBase.getInstanceNoteBD(getApplicationContext()).getNoteDao().insertNote(note);

                for (int k = 0; k < listEdt.size(); k++) {
                    EditText editText = listEdt.get(k);
                    String paragraphTxt = editText.getText().toString();
                    if (!paragraphTxt.isEmpty()) {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setText(paragraphTxt);
                        paragraph.setPosition(k);
                        paragraph.setNoteId(resul);
                        insertParagraphDB(paragraph);
                    }
                }
            } catch (Exception e) {
                System.out.println("Excepcion - Note " + e.getMessage() + " - " + e.getCause());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Se guardaron notas", Toast.LENGTH_SHORT).show();
        }
    }
    private void insertParagraphDB(Paragraph paragraph) {
        new TaskAddParagraph().execute(paragraph);
    }
    private class TaskAddParagraph extends AsyncTask<Paragraph, Void, Void> {
        @Override
        protected Void doInBackground(Paragraph... paragraphs) {
            try {
                AppDataBase.getInstanceParagraphBD(getApplicationContext()).getParagraphDao().insertParagraph(paragraphs);
            } catch (Exception e) {
                System.out.println("Excepcion - Paragraph " + e.getMessage() + " - " + e.getCause());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Se guardaron titulos con Notas", Toast.LENGTH_SHORT).show();
        }
    }
}
