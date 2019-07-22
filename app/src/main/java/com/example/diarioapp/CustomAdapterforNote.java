package com.example.diarioapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

public class CustomAdapterforNote extends RecyclerView.Adapter<CustomAdapterforNote.ViewHolder> {
    Context context;
    ArrayList<Note> list;
    public CustomAdapterforNote(Context context, ArrayList<Note> list){
        this.context=context;
        this.list=list;

    }

    @Override
    public CustomAdapterforNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view= LayoutInflater.from(context).inflate(R.layout.each_note,viewGroup,false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterforNote.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText mis_notas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //mis_notas=itemView.findViewById(R.id.mis_notas);

        }
    }
}
