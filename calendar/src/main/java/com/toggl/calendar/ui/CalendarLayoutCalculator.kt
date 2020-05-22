package com.toggl.calendar.ui

import com.toggl.environment.services.time.TimeService
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.absoluteValue

typealias CalendarItemGroup = MutableList<Pair<Int, CalendarItem>>
typealias CalendarItemGroups = MutableList<CalendarItemGroup>

class CalendarLayoutCalculator @Inject constructor(private val timeService: TimeService) {

    private val offsetFromNow: Duration = Duration.ofMinutes(7)
    private val minimumDurationForUIPurposes: Duration = Duration.ofMinutes(15)

    fun calculateLayoutAttributes(calendarItems: List<CalendarItem>): List<CalendarItemLayoutAttributes> {
        if (calendarItems.isEmpty())
            return emptyList()

        return calendarItems
            .mapIndexed { index, calendarItem -> index to calendarItem }
            .sortedBy { (_, item) -> item.startTime() }
            .fold(mutableListOf(), ::groupOverlappingItems)
            .flatMap(::calculateLayoutAttributesFor)
            .sortedBy { (index, _) -> index }
            .map { (_, item) -> item }
            .toList()
    }

    /**
     * Aggregates the indexed calendar items into buckets. Each bucket contains the sequence of overlapping items.
     * The items in a bucket don't overlap all with each other, but cannot overlap with items in other buckets.
     *
     * @param buckets   The list of agregated items
     * @param indexedCalendarItem   The item to put in a bucket
     * @return A list of buckets
     */
    private fun groupOverlappingItems(
        buckets: CalendarItemGroups,
        indexedCalendarItem: Pair<Int, CalendarItem>
    ): CalendarItemGroups {
        if (buckets.isEmpty()) {
            buckets.add(mutableListOf(indexedCalendarItem))
            return buckets
        }

        val now = timeService.now()
        val group = buckets.last()
        val maxEndTime = group.map { (_, item) -> calculateEndTimeWith(item, now) }.max() ?: OffsetDateTime.MIN
        if (indexedCalendarItem.second.startTime() < maxEndTime)
            group.add(indexedCalendarItem)
        else
            buckets.add(mutableListOf(indexedCalendarItem))

        return buckets
    }

    /**
     * Calculates the layout attributes for the indexed calendar items in a bucket.
     * The calculation is done minimizing the number of columns.
     *
     * @param bucket
     * @return An list of indexed calendar attributes
     */
    private fun calculateLayoutAttributesFor(bucket: CalendarItemGroup): List<Pair<Int, CalendarItemLayoutAttributes>> {
        val left = bucket.filter { indexedItem -> indexedItem.second is CalendarItem.CalendarEvent }.toMutableList()
        val right = bucket.filter { indexedItem -> indexedItem.second is CalendarItem.TimeEntry }.toMutableList()

        val leftColumns = calculateColumnsForItemsInSource(left)
        val rightColumns = calculateColumnsForItemsInSource(right)

        val groupColumns = leftColumns + rightColumns

        return groupColumns
            .mapIndexed { columnIndex, column ->
                column.map { item -> item.first to attributesForItem(item.second, groupColumns.size, columnIndex) }
            }
            .flatten()
    }

    private fun attributesForItem(
        calendarItem: CalendarItem,
        totalColumns: Int,
        columnIndex: Int
    ): CalendarItemLayoutAttributes {
        val now = timeService.now()
        return CalendarItemLayoutAttributes(
            calendarItem.startTime(),
            calendarItem.duration(now, offsetFromNow),
            totalColumns,
            columnIndex
        )
    }

    private fun calculateColumnsForItemsInSource(bucket: CalendarItemGroup): CalendarItemGroups =
        bucket.fold(mutableListOf(), ::convertIntoColumns)

    /**
     * Aggregates the items into columns, minimizing the number of columns.
     * This will try to insert an item into the first column without overlapping with other items there,
     * if that's not possible, will try with the rest of the columns until it's inserted or a new column is required.
     *
     * @param bucket
     * @param indexedItem
     */
    private fun convertIntoColumns(bucket: CalendarItemGroups, indexedItem: Pair<Int, CalendarItem>): CalendarItemGroups {
        if (bucket.isEmpty()) {
            bucket.add(mutableListOf(indexedItem))
            return bucket
        }

        val (column, position) = columnAndPositionToInsertItem(bucket, indexedItem)

        if (column != null) {
            column.add(position, indexedItem)
        } else {
            bucket.add(mutableListOf(indexedItem))
        }

        return bucket
    }

    /**
     * Returns the column and position in that column to insert the new item.
     * If the item cannot be inserted, the column is null.
     *
     * @param columns
     * @param item
     */
    private fun columnAndPositionToInsertItem(
        columns: CalendarItemGroups,
        item: Pair<Int, CalendarItem>
    ): Pair<CalendarItemGroup?, Int> {
        var positionToInsert = -1
        val now = timeService.now()
        val column = columns.firstOrNull {
            val lastItem = it.lastOrNull { el -> calculateEndTimeWith(el.second, now) <= item.second.startTime() }
            val index = it.lastIndexOf(lastItem)
            when {
                index < 0 -> false
                index == it.size - 1 -> {
                    positionToInsert = it.size
                    true
                }
                it[index + 1].second.startTime() >= calculateEndTimeWith(item.second, now) -> {
                    positionToInsert += 1
                    true
                }
                else -> false
            }
        }
        return column to positionToInsert
    }

    private fun calculateEndTimeWith(item: CalendarItem, now: OffsetDateTime): OffsetDateTime {
        val duration = item.duration(now, offsetFromNow)
        return if (duration <= minimumDurationForUIPurposes)
            item.startTime() + minimumDurationForUIPurposes
        else item.endTime(now, offsetFromNow)
    }

    private fun CalendarItem.endTime(now: OffsetDateTime, offsetFromNow: Duration = Duration.ZERO): OffsetDateTime {
        return endTime() ?: now + offsetFromNow
    }

    private fun CalendarItem.duration(now: OffsetDateTime, offsetFromNow: Duration = Duration.ZERO): Duration = when (this) {
        is CalendarItem.TimeEntry -> timeEntry.duration ?: ((now + offsetFromNow).absoluteDurationBetween(startTime()))
        is CalendarItem.CalendarEvent -> calendarEvent.duration
    }

    private fun OffsetDateTime.absoluteDurationBetween(other: OffsetDateTime): Duration =
        Duration.ofMillis(ChronoUnit.MILLIS.between(this, other).absoluteValue)
}