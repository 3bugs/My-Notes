package com.promlert.mynotes;

/**
 * Created by Promlert on 1/16/2016.
 */
public class Note {

    public final int noteId;
    public final String text;
    public final boolean isImportant;

    public Note(int noteId, String text, boolean isImportant) {
        this.noteId = noteId;
        this.text = text;
        this.isImportant = isImportant;
    }

    @Override
    public String toString() {
        return String.valueOf(noteId)
                + " - "
                + text
                + " - "
                + String.valueOf(isImportant);
    }
}
