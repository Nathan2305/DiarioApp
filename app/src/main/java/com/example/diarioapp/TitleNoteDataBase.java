package com.example.diarioapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {TitleNote.class},version = 1)
public abstract class TitleNoteDataBase extends RoomDatabase {

    private static TitleNoteDataBase instance = null;

    public static TitleNoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, TitleNoteDataBase.class, "title_notes.db").build();
        }
        return instance;
    }

    public abstract TitleNoteDao getTitleNoteDao();

}
