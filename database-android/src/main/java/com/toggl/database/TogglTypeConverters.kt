package com.toggl.database

import androidx.room.TypeConverter
import com.toggl.models.domain.WorkspaceFeature
import java.time.Duration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

class TogglTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): OffsetDateTime? =
        value?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) }

    @TypeConverter
    fun toTimestamp(date: OffsetDateTime?): Long? {
        return date?.run { toInstant().toEpochMilli() }
    }

    @TypeConverter
    fun fromEpoch(value: Long?): Duration? =
        value?.let { Duration.ofMillis(it) }

    @TypeConverter
    fun toEpoch(duration: Duration?): Long? {
        return duration?.run { toMillis() }
    }

    @TypeConverter
    fun fromFeatureList(value: String?): List<WorkspaceFeature>? =
        value?.run {
            split(',')
                .map(String::toInt)
                .mapNotNull(WorkspaceFeature.Companion::fromFeatureId)
        }

    @TypeConverter
    fun toFeatureList(value: List<WorkspaceFeature>?): String? =
        value?.run {
            joinToString(",") { it.featureId.toString() }
        }
}
