package com.example.diarioapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Note {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_note")
    private String id_note;

    @ColumnInfo(name = "dateText")
    private String dateText;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name="position")
    private int position;

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

    @NonNull
    public String getId_note() {
        return id_note;
    }

    public void setId_note(@NonNull String id_note) {
        this.id_note = id_note;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
