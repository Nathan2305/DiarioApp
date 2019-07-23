package com.example.diarioapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase instance = null;

    public static NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDataBase.class, "notes.db").build();
        }
        return instance;
    }

    public abstract NoteDao getnoteDao();

}
