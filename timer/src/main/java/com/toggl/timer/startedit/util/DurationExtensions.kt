package com.toggl.timer.startedit.util

import java.time.Duration

fun Duration.asDurationString() =
    "%02d:%02d:%02d".format(toHours(), toMinutes() % 60, seconds % 60)