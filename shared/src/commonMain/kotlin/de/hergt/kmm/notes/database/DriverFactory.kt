package de.hergt.kmm.notes.database

import app.cash.sqldelight.db.SqlDriver
import de.hergt.kmm.notes.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}