package com.example.diarioapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterforNote extends RecyclerView.Adapter<CustomAdapterforNote.ViewHolder> {
    Context context;
    List<String> list;

    public CustomAdapterforNote(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public CustomAdapterforNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_note_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterforNote.ViewHolder viewHolder, int i) {
        viewHolder.title_note.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_note=itemView.findViewById(R.id.title_note);

        }
    }
}
