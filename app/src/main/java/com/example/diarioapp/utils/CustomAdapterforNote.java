package com.example.diarioapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diarioapp.R;
import com.example.diarioapp.entities.pojo.Note;

import java.util.List;

public class CustomAdapterforNote extends RecyclerView.Adapter<CustomAdapterforNote.ViewHolder> implements View.OnLongClickListener {
    Context context;
    List<Note> list;
    private OnItemClickListener mListener;

    public CustomAdapterforNote(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CustomAdapterforNote.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_note_card, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterforNote.ViewHolder viewHolder, int i) {
        viewHolder.title_note.setText(list.get(i).getTitle());
        viewHolder.title_date.setText(list.get(i).getDateTitlenote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onLongClick(View v) {
        Util.showToast(context, "Se mantuvo presionado mucho tiempo");
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_note, title_date;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title_note = itemView.findViewById(R.id.title_note);
            title_date = itemView.findViewById(R.id.title_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onDeleteItemClick(position);
                        if (position != -1 && position < list.size()) {
                            //listener.onDeleteItemClick(position);
                           /* list.remove(position);
                            notifyItemChanged(position);
                            notifyItemRangeChanged(position,getItemCount());*/
                        }

                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
