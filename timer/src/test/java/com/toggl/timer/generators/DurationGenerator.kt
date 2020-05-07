package com.toggl.timer.generators

import io.kotlintest.properties.Gen
import java.time.Duration

fun Gen.Companion.threeTenDuration(): Gen<Duration> =
    positiveLong().map(Duration::ofMillis)