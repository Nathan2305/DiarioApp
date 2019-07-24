package com.example.diarioapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TitleNoteDao {
    @Query("SELECT *FROM TitleNote")
    List<TitleNote> getAll();

    @Insert
    void insertTitleNote(TitleNote... titleNote);
}
