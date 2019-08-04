package com.example.diarioapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.diarioapp.entities.pojo.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert
    void inserPhoto(Photo... photos);

    @Query("SELECT *FROM Photo WHERE Photo.noteId= :noteId")
    public List<Photo> getListPhotoperNote(long noteId);
}
