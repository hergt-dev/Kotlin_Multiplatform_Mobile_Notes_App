package de.hergt.kmm.notes.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import de.hergt.kmm.notes.Database
import de.hergt.kmm.notes.NotesQueries

actual class DatabaseFactory(private val context: Context) {

    actual val mDatabase: Database
        get() = Database(createDriver())

    actual val mNotesQueries: NotesQueries
        get() = mDatabase.notesQueries

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "notes.db")
    }
}