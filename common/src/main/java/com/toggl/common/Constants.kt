package com.toggl.common

object Constants {
    const val timeEntryDeletionDelayMs = 5000L

    object Vibration {
        const val defaultDurationInMillis = 10L
        val oldApisVibrationPattern = longArrayOf(0, 10)
    }
}
