package com.toggl.calendar.ui

import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime

data class CalendarItemLayoutAttributes(
    val startTime: OffsetDateTime,
    val duration: Duration,
    val totalColumns: Int,
    val columnIndex: Int
)