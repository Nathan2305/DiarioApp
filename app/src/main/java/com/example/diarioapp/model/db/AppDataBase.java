package com.example.diarioapp.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.diarioapp.dao.NoteDao;
import com.example.diarioapp.dao.ParagraphDao;
import com.example.diarioapp.entities.pojo.Paragraph;
import com.example.diarioapp.entities.pojo.Note;

@Database(entities = {Note.class, Paragraph.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance_paragraph = null;
    private static AppDataBase instance_note = null;

    public static AppDataBase getInstanceParagraphBD(Context context) {

        if (instance_paragraph == null) {
            instance_paragraph = Room.databaseBuilder(context, AppDataBase.class, "paragraph.db").build();
        }
        return instance_paragraph;
    }

    public static AppDataBase getInstanceNoteBD(Context context) {
        if (instance_note == null) {
            instance_note = Room.databaseBuilder(context, AppDataBase.class, "note.db").build();
        }
        return instance_note;
    }

    public abstract ParagraphDao getParagraphDao();
    public abstract NoteDao getNoteDao();

}
