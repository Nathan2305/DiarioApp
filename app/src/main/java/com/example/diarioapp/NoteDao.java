package com.example.diarioapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT *FROM Note")
    List<Note> getAll();

    @Insert
    void insertNotes(Note... notes);
}
