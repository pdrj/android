package com.toggl.timer.startedit.ui.editduration.model

import com.toggl.timer.startedit.util.MathConstants
import java.time.Duration
import kotlin.math.min

data class DurationFieldInfo(val digits: List<Int> = emptyList()) {
    fun minutes() = combineDigitsIntoANumber(0, 2)
    fun hours() = combineDigitsIntoANumber(2, 3)
    fun isEmpty() = digits.isEmpty()

    fun push(digit: Int): DurationFieldInfo {
        if (digit < 0 || digit > 9)
            throw IllegalArgumentException("Digits must be between 0 and 9, value $digit was rejected.")

        if (digits.size == maximumNumberOfDigits) return this

        if (digits.isEmpty() && digit == 0) return this

        return DurationFieldInfo(listOf(digit) + digits)
    }

    fun pop(): DurationFieldInfo {
        if (digits.isEmpty()) return this
        return DurationFieldInfo(digits.subList(1, digits.size))
    }

    fun toDuration(): Duration =
        Duration.ofHours(hours().toLong()).plusMinutes(minutes().toLong()).coerceIn(Duration.ZERO, maximumDuration)

    private fun combineDigitsIntoANumber(start: Int, count: Int): Int {
        val digitsArray = digits.toIntArray()
        var number = 0
        var power = 1
        for (i in start until min(start + count, digitsArray.size)) {
            number += digitsArray[i] * power
            power *= 10
        }
        return number
    }

    override fun toString(): String {
        return "%02d:%02d".format(hours(), minutes())
    }

    override fun hashCode(): Int = digits.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DurationFieldInfo

        return hours() == other.hours() && minutes() == other.minutes()
    }

    companion object {
        val maximumNumberOfDigits = 5
        val maximumDuration = Duration.ofHours(999)

        fun fromDuration(duration: Duration): DurationFieldInfo {
            val digits = mutableListOf<Int>()
            val totalMinutes = duration.coerceIn(Duration.ZERO, maximumDuration).seconds / MathConstants.secondsInAMinute
            val hoursPart = totalMinutes / 60
            val minutesPart = totalMinutes % 60
            val digitsString = (hoursPart * 100 + minutesPart).toString()

            digitsString.toCharArray()
                .map { it - '0' }
                .forEach { digits.add(0, it) }

            return DurationFieldInfo(digits.toMutableList())
        }
    }
}