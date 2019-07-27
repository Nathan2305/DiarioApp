package com.example.diarioapp.model.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.diarioapp.utils.CustomAdapterforNote;
import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Note;
import com.example.diarioapp.model.db.AppDataBase;
import com.example.diarioapp.utils.Util;

import java.util.List;

public class ActivityListNotes extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recycler_notes;
    /// RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        recycler_notes = findViewById(R.id.recycler_notes);
        layoutManager = new LinearLayoutManager(getApplicationContext());
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

    private class TaskGetNotes extends AsyncTask<Void, Void, List<Note>> {

        @Override
        protected List<Note> doInBackground(Void... voids) {
            return AppDataBase.getInstanceNoteBD(getApplicationContext()).getNoteDao().getAllNotes();
        }

        @Override
        protected void onPostExecute(final List<Note> notes) {
            super.onPostExecute(notes);
            if (!notes.isEmpty()) {
                RecyclerView.Adapter adapter = new CustomAdapterforNote(getApplicationContext(), notes);
                recycler_notes.setLayoutManager(layoutManager);
                recycler_notes.setAdapter(adapter);
                recycler_notes.hasFixedSize();
                ((CustomAdapterforNote) adapter).setOnItemClickListener(new CustomAdapterforNote.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), Activity_savedParagraph.class);
                        intent.putExtra("noteId",notes.get(position).getNoteId());
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClick(int position) {

                    }
                });

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
