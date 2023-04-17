package de.hergt.kmm.core

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeUtils {

    companion object {
        private fun Int.twoDigits(): String {
            return if (this in 0..9) "0$this" else "$this"
        }
    }

    private fun currentTimeInstant(): Instant {
        return Clock.System.now()
    }

    private fun timeMillisToLocalDateTime(timeMillis: Long): LocalDateTime {
        return Instant
            .fromEpochMilliseconds(timeMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun currentTimeMillis(): Long {
        return currentTimeInstant().toEpochMilliseconds()
    }

    fun convertTimeMillisToDateString(timeMillis: Long): String {
        val date = timeMillisToLocalDateTime(timeMillis).date
        return "${date.dayOfMonth.twoDigits()}.${date.monthNumber.twoDigits()}.${date.year}"
    }

}