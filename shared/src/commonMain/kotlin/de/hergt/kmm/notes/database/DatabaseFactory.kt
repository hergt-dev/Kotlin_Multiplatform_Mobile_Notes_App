package de.hergt.kmm.notes.database

import app.cash.sqldelight.db.SqlDriver
import de.hergt.kmm.notes.Database
import de.hergt.kmm.notes.NotesQueries

expect class DatabaseFactory {

    val mDatabase: Database

    val mNotesQueries: NotesQueries

    fun createDriver(): SqlDriver
}