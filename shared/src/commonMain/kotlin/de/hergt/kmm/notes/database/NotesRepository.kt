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

    fun getNoteById(id: Int): Flow<Notes> {
        return mQueries.getNoteById(id.toLong()).asFlow().mapToOneNotNull(Dispatchers.Default)
    }

    fun createNote(id: Int, title: String, text: String, date: Long) {
        mQueries.setNoteByValues(id.toLong(), title, text, date)
    }

    fun createNote(note: Notes) {
        mQueries.setNoteByObject(note)
    }

    fun updateNote(id: Int, title: String? = null, text: String? = null, date: Long? = null) {
        mQueries.updateNoteByValues(title, text, date, id.toLong())
    }

    fun deleteNotes() {
        mQueries.deleteNotes()
    }

    fun deleteNoteById(id: Int) {
        mQueries.deleteNotesById(id.toLong())
    }

}