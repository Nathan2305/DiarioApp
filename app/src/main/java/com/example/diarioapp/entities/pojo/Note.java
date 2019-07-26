package com.example.diarioapp.entities.pojo;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "noteId")
    private long noteId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "dateTitlenote")
    private String dateTitlenote;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTitlenote() {
        return dateTitlenote;
    }

    public void setDateTitlenote(String dateTitlenote) {
        this.dateTitlenote = dateTitlenote;
    }
}
