package com.example.diarioapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class ActivityListNotes extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recycler_notes;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        recycler_notes=findViewById(R.id.recycler_notes);
        layoutManager=new LinearLayoutManager(getApplicationContext());

        fab = findViewById(R.id.add_note);
        new TaskGetNotes().execute();
        setListeners();

    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TitleNoteActivity.class);
                startActivity(i);
            }
        });
    }

    private class TaskGetNotes extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            return NoteDataBase.getInstance(getApplicationContext()).getnoteDao().getTitles();
        }

        @Override
        protected void onPostExecute(List<String> notes) {
            super.onPostExecute(notes);
            adapter=new CustomAdapterforNote(getApplicationContext(),notes);
            recycler_notes.setLayoutManager(layoutManager);
            recycler_notes.setAdapter(adapter);

        }
    }
}
