package com.toggl.timer.common.domain.extensions

import com.toggl.models.domain.EditableTimeEntry

fun EditableTimeEntry.isNew() = this.startTime == null
fun EditableTimeEntry.isRunning() = this.duration == null
fun EditableTimeEntry.isRunningOrNew() = isRunning() || isNew()
fun EditableTimeEntry.isStopped() = this.startTime != null && this.duration != null