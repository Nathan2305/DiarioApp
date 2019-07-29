package com.example.diarioapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Paragraph;

import java.util.List;

public class CustomAdapterParagraph extends RecyclerView.Adapter<CustomAdapterParagraph.ViewHolder> {

    private Context context;
    private List<Paragraph> list;

    public CustomAdapterParagraph(Context context, List<Paragraph> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomAdapterParagraph.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.each_paragraph_saved,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterParagraph.ViewHolder viewHolder, int i) {
            viewHolder.paragraph.setText(list.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView paragraph;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paragraph=itemView.findViewById(R.id.paragraph);
        }
    }
}
