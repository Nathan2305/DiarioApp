package com.example.diarioapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Note_Activity extends AppCompatActivity {
    FloatingActionButton fab, fab_save_notes;
    LinearLayout linearLayout;
    ArrayList<Note> lisNote;
    ArrayList<EditText> listEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lisNote = new ArrayList<>();
        linearLayout = findViewById(R.id.container_ll);
        listEdt = new ArrayList<>();
        fab = findViewById(R.id.fab_add);
        fab_save_notes = findViewById(R.id.fab_save_notes);
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
        fab_save_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int k = 0; k < listEdt.size(); k++) {
                    EditText editText = listEdt.get(k);
                    String nota = editText.getText().toString();
                    if (!nota.isEmpty()) {
                        Note note = new Note();
                        note.setTitle(nota);
                        lisNote.add(note);
                    }
                }
            }
        });
    }
}
