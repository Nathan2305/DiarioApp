package com.example.diarioapp.model.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Paragraph;
import com.example.diarioapp.model.db.AppDataBase;
import com.example.diarioapp.utils.CustomAdapterParagraph;

import java.util.List;

public class Activity_savedParagraph extends AppCompatActivity {
    RecyclerView rec;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_paragraph);
        rec = findViewById(R.id.rec);
        Bundle bundle = getIntent().getExtras();
        long noteId = bundle.getLong("noteId");
        showParagraphs(noteId);

    }

    private void showParagraphs(long noteId) {
        new TaskgetParragraphs().execute(noteId);
    }

    private class TaskgetParragraphs extends AsyncTask<Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... voids) {
            try {
                List<Paragraph> list=AppDataBase.getInstanceParagraphBD(getApplicationContext()).
                        getParagraphDao().getListParagraphPerNote(voids[0]);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                adapter=new CustomAdapterParagraph(getApplicationContext(),list);
                rec.setLayoutManager(layoutManager);
                rec.hasFixedSize();
                rec.setAdapter(adapter);

            }catch (Exception e){
                System.out.println("Excepcion gettting listParagraphs - "+e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
