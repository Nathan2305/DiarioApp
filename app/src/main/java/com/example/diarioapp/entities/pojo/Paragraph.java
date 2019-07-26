package com.example.diarioapp.entities.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity

public class Paragraph {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "paragraphId")
    private long paragraphId;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name="position")
    private int position;

    @ColumnInfo(name="noteId")
    private long noteId;


    public long getParagraphId() {
        return paragraphId;
    }

    public void setParagraphId(long paragraphId) {
        this.paragraphId = paragraphId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
