package com.example.diarioapp.entities.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "photo_id")
    private long photo_id;

    @ColumnInfo(name = "noteId")
    private long noteId;

    @ColumnInfo(name = "photo_name")
    private String photo_name;

    public long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(long photo_id) {
        this.photo_id = photo_id;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }
}
