package com.toggl.environment.services.time

import java.time.OffsetDateTime

interface TimeService {
    fun now(): OffsetDateTime
}
