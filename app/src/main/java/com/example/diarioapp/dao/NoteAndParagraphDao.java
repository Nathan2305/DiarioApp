package com.example.diarioapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.example.diarioapp.entities.pojo.Paragraph;

import java.util.List;

@Dao
public interface NoteAndParagraphDao {
    @Query("SELECT *FROM PARAGRAPH WHERE noteId= :noteId")
    public List<Paragraph> getListParagraph(long noteId);
}
