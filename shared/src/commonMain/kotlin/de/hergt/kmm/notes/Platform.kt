package de.hergt.kmm.notes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform