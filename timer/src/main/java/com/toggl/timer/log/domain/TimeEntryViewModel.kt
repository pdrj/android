package com.toggl.timer.log.domain

import androidx.core.graphics.toColorInt
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import java.time.Duration
import java.time.OffsetDateTime

sealed class TimeEntryViewModel

interface TimeEntryContentViewModel {
    val description: String
    val duration: Duration
    val project: ProjectViewModel?
    val billable: Boolean
    val hasTags: Boolean
}

data class FlatTimeEntryViewModel(
    val id: Long,
    override val description: String,
    val startTime: OffsetDateTime,
    override val duration: Duration,
    override val project: ProjectViewModel?,
    override val billable: Boolean,
    override val hasTags: Boolean
) : TimeEntryViewModel(), TimeEntryContentViewModel

data class ProjectViewModel(
    val id: Long,
    val name: String,
    val color: String,
    val clientName: String?
)

fun ProjectViewModel?.formatForDisplay() =
    if (this == null) ""
    else
        buildSpannedString {
            color(color.toColorInt()) {
                append(name)
            }
            append(" ")
            append(clientName ?: "")
        }

data class DayHeaderViewModel(
    val dayTitle: String,
    val totalDuration: Duration
) : TimeEntryViewModel()

data class TimeEntryGroupViewModel(
    val groupId: Long,
    val timeEntryIds: List<Long>,
    val isExpanded: Boolean,
    override val description: String,
    override val duration: Duration,
    override val project: ProjectViewModel?,
    override val billable: Boolean,
    override val hasTags: Boolean
) : TimeEntryViewModel(), TimeEntryContentViewModel