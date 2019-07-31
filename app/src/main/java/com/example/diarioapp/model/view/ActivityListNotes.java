package com.example.diarioapp.model.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.io.File;
import java.util.List;

public class ActivityListNotes extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recycler_notes;
    private String sdCardState = "";
    public static String FOLDER_PICTURE = "";
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Note> listNotes;
    private int REQUEST_WRITE_EXTERNAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        recycler_notes = findViewById(R.id.recycler_notes);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        fab = findViewById(R.id.add_note);
        new TaskGetNotes().execute();
        setListeners();
        checkToWriteExternal();
    }

    private void checkToWriteExternal() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL);
        } else {
            createDirectorySource();
        }
    }

    private void createDirectorySource() {
        sdCardState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(sdCardState)) {
            FOLDER_PICTURE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SuperNotesApp/Picture";
            File file = new File(FOLDER_PICTURE);
            if (!file.exists()) {
                try {
                    if (file.mkdirs()) {  //crea directios incluyendo la carpeta padre
                        Util.showToast(getApplicationContext(), "Se creó el directorio " + FOLDER_PICTURE);
                    } else {
                        Util.showToast(getApplicationContext(), "No se creó el directorio " + FOLDER_PICTURE);
                    }
                } catch (SecurityException e) {
                    System.out.println("Excepcion reading File " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createDirectorySource();
            }
        }
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
                listNotes = notes;
                adapter = new CustomAdapterforNote(getApplicationContext(), listNotes);
                recycler_notes.setLayoutManager(layoutManager);
                recycler_notes.setAdapter(adapter);
                recycler_notes.hasFixedSize();
                ((CustomAdapterforNote) adapter).setOnItemClickListener(new CustomAdapterforNote.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), Activity_savedParagraph.class);
                        intent.putExtra("noteTitle", listNotes.get(position).getTitle());
                        intent.putExtra("noteId", listNotes.get(position).getNoteId());
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteItemClick(int position) {
                        /*listNotes.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, listNotes.size());*/
                    }
                });
            }
        }
    }
}
