package com.example.diarioapp.model.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    FloatingActionButton fab_add, fab_text, fab_adio, fab_img;
    LinearLayout linearLayout;
    ArrayList<EditText> listEdt;
    TextView titleNote;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleNote = findViewById(R.id.titleNote);
        Intent intent = getIntent();
        linearLayout = findViewById(R.id.container_ll);
        listEdt = new ArrayList<>();

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        String title_get = intent.getExtras().getString("title_note");
        if (!title_get.isEmpty()) {
            titleNote.setText(title_get);
        }

        fab_add = findViewById(R.id.fab_add);
        fab_text = findViewById(R.id.fab_text);
        fab_adio = findViewById(R.id.fab_audio);
        fab_img = findViewById(R.id.fab_image);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnimation();
            }
        });
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP){
            fab_text.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.celeste)));
            fab_adio.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.naranja)));
            fab_img.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bground)));
        }

        fab_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View constraintLayout = layoutInflater.inflate(R.layout.each_paragraph_to_write, null);
                LinearLayout container = constraintLayout.findViewById(R.id.container);

                EditText editText = new EditText(getApplicationContext());
                editText.setBackground(null);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setPadding(35, 35, 35, 35);
                editText.requestFocus();

                listEdt.add(editText);
                container.addView(editText);
                linearLayout.addView(constraintLayout);
            }
        });

    }

    private void handleAnimation() {
        if (isOpen) {
            fab_add.startAnimation(rotateForward);
            fab_adio.startAnimation(fabClose);
            fab_img.startAnimation(fabClose);
            fab_text.startAnimation(fabClose);
            fab_text.setClickable(false);
            fab_adio.setClickable(false);
            fab_img.setClickable(false);
            isOpen = false;
        } else {
            fab_add.startAnimation(rotateBackward);
            fab_adio.startAnimation(fabOpen);
            fab_img.startAnimation(fabOpen);
            fab_text.startAnimation(fabOpen);
            fab_text.setClickable(true);
            fab_adio.setClickable(true);
            fab_img.setClickable(true);
            isOpen = true;
        }
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
                long resul = AppDataBase.getInstanceNoteBD(getApplicationContext()).getNoteDao().insertNote(note);

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
                Intent i1 = new Intent(getApplicationContext(), ActivityListNotes.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // <= USE THIS
                startActivity(i1);
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
