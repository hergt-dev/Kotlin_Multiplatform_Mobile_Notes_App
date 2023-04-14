package de.hergt.kmm.notes.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import de.hergt.kmm.notes.Database
import de.hergt.kmm.notes.NotesQueries

actual class DatabaseFactory {

    actual val mDatabase: Database
        get() = Database(createDriver())

    actual val mNotesQueries: NotesQueries
        get() = mDatabase.notesQueries

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "notes.db")
    }
}