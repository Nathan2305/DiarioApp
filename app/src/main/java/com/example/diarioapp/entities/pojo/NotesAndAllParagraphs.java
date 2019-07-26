package com.example.diarioapp.entities.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

public class NotesAndAllParagraphs {

    @Embedded
    public Note note;

    @Relation(parentColumn = "paragraphId", entityColumn = "noteId", entity = Paragraph.class)
    public List<Paragraph> paragraps;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public List<Paragraph> getParagraps() {
        return paragraps;
    }

    public void setParagraps(List<Paragraph> paragraps) {
        this.paragraps = paragraps;
    }
}
