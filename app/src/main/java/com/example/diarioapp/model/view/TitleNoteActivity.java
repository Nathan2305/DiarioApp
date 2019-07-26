package com.example.diarioapp.model.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diarioapp.R;

public class TitleNoteActivity extends AppCompatActivity {
    EditText titleNote;
    FloatingActionButton fab_go_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_note);
        titleNote = findViewById(R.id.titleNote);
        fab_go_note = findViewById(R.id.fab_go_note);
        fab_go_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleNote.getText().toString().trim();
                if (!title.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), Note_Activity.class);
                    intent.putExtra("title_note", title);
                    startActivity(intent);
                }
            }
        });
        titleNote.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        titleNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    titleNote.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(titleNote.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

}
