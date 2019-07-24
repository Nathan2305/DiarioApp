package com.example.diarioapp;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import java.util.List;

@Entity
public class TitleNote {
    @PrimaryKey
    @NonNull
    private String id_title_note;

    @ColumnInfo(name = "dateTitlenote")
    private String dateTitlenote;

    @ColumnInfo(name = "title")
    private String title;

    /*@Relation(parentColumn = "id_title_note", entityColumn = "id_note", entity = Note.class)
    private List<Note> notes;*/

    @NonNull
    public String getId_title_note() {
        return id_title_note;
    }

    public void setId_title_note(@NonNull String id_title_note) {
        this.id_title_note = id_title_note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   /* public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }*/

    public String getDateTitlenote() {
        return dateTitlenote;
    }

    public void setDateTitlenote(String dateTitlenote) {
        this.dateTitlenote = dateTitlenote;
    }
}
