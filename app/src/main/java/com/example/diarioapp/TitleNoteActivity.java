package com.example.diarioapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TitleNoteActivity extends AppCompatActivity {
    EditText titleNote;
    FloatingActionButton fab_go_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_note);
        titleNote=findViewById(R.id.titleNote);
        fab_go_note=findViewById(R.id.fab_go_note);
        fab_go_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleNote.getText().toString();
                if (!title.isEmpty()){
                    Intent intent=new Intent(getApplicationContext(),Note_Activity.class);
                    intent.putExtra("title_note",title);
                    startActivity(intent);
                }
            }
        });
    }
}
