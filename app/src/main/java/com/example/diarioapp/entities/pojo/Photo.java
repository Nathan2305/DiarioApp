package com.example.diarioapp.entities.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Blob;

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "photo_id")
    private long photo_id;

    @ColumnInfo(name = "noteId")
    private long noteId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] img;

    @ColumnInfo(name = "path")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
