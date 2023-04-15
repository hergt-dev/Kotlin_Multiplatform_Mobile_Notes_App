package de.hergt.kmm.notes.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneNotNull
import de.hergt.kmm.notes.Notes
import de.hergt.kmm.notes.NotesQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val mQueries: NotesQueries) {

    fun getNotes(): Flow<List<Notes>> {
        return mQueries.getNotes().asFlow().mapToList(Dispatchers.Default)
    }

    fun getNoteById(id: Long): Flow<Notes> {
        return mQueries.getNoteById(id).asFlow().mapToOneNotNull(Dispatchers.Default)
    }

    fun createNote(note: Notes) {
        mQueries.setNoteByObject(note)
    }

    fun updateNote(note: Notes) {
        mQueries.updateNoteByValues(note.mTitle, note.mText, note.mDate, note.mId)
    }

    fun deleteNoteById(id: Long) {
        mQueries.deleteNotesById(id)
    }

}