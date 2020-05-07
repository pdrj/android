package com.toggl.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.OffsetDateTime

@Entity(
    tableName = "time_entries",
    foreignKeys = [
        ForeignKey(entity = DatabaseProject::class, parentColumns = ["id"], childColumns = ["projectId"]),
        ForeignKey(entity = DatabaseWorkspace::class, parentColumns = ["id"], childColumns = ["workspaceId"]),
        ForeignKey(entity = DatabaseTask::class, parentColumns = ["id"], childColumns = ["taskId"])
    ],
    indices = [Index("projectId")]
)
data class DatabaseTimeEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String,
    val startTime: OffsetDateTime,
    val duration: Duration?,
    val billable: Boolean,
    val workspaceId: Long,
    val projectId: Long?,
    val taskId: Long?,
    val isDeleted: Boolean
)