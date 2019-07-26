package com.example.diarioapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.diarioapp.entities.pojo.Paragraph;

import java.util.List;
@Dao
public interface ParagraphDao {

    @Query("SELECT *FROM Paragraph")
    List<Paragraph> getAllParagraph();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertParagraph(Paragraph... paragraph);


}
