package de.hergt.kmm.notes.android.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.hergt.kmm.core.TimeUtils
import de.hergt.kmm.core.extensions.isNotNull
import de.hergt.kmm.notes.Notes
import de.hergt.kmm.notes.database.DatabaseFactory
import de.hergt.kmm.notes.database.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class NotesViewModel(context: Context): ViewModel() {

    private val mDatabase = DatabaseFactory(context)
    private val mNotesRepository = NotesRepository(mDatabase.mNotesQueries)

    val mTimeUtil = TimeUtils()

    private val _note: MutableStateFlow<Notes?> = MutableStateFlow(null)
    val selectedNote = _note.asStateFlow()

    fun getAllNotes(): Flow<List<Notes>> {
        return mNotesRepository.getNotes()
    }

    fun upsertNote(note: Notes) {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext) { getAllNotes().firstOrNull() }?.let { noteList ->
                if (noteList.firstOrNull { it.mId == note.mId }.isNotNull()) {
                    mNotesRepository.updateNote(note)
                } else {
                    mNotesRepository.createNote(note)
                }
            }
        }
    }

    fun deleteNoteById(id: Long) {
        mNotesRepository.deleteNoteById(id)
    }

    fun selectNewNote() {
        _note.value = Notes(
            UUID.randomUUID().mostSignificantBits,
            null,
            null,
            mTimeUtil.currentTimeMillis()
        )
    }

    fun selectNote(id: Long) {
        viewModelScope.launch {
            getNoteById(id).firstOrNull()?.let {
                _note.value = it
            }
        }
    }

    fun unselectNote() {
        _note.value = null
    }

    fun updateTitle(value: String) {
        _note.value = _note.value?.copy(mTitle = value)
    }

    fun updateText(value: String) {
        _note.value = _note.value?.copy(mText = value)
    }

    private fun getNoteById(id: Long): Flow<Notes> {
        return mNotesRepository.getNoteById(id)
    }

}