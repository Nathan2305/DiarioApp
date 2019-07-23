package com.example.diarioapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Note {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dateText")
    private String dateText;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name="titleNote")
    private String titleNote;

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }
}
