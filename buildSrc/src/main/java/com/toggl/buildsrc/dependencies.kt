package com.toggl.buildsrc

object Versions {
    const val ktlint = "0.29.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.6.1"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.28.0"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.2"
    // fixes warning thrown by SLF4J
    const val slf4j = "org.slf4j:slf4j-simple:1.7.26"
    const val threeTen = "org.threeten:threetenbp:1.4.2"
    const val threeTenAndroid = "com.jakewharton.threetenabp:threetenabp:1.2.2"

    object Test {
        const val junit4 = "junit:junit:4.13"
        const val robolectric = "org.robolectric:robolectric:4.3.1"
        const val junit5Plugin = "de.mannodermaus.gradle.plugins:android-junit5:1.5.0.0"
        const val kotlinTestRunner = "io.kotlintest:kotlintest-runner-junit5:3.4.2"
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:1.3.71"
        const val kotlinCoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.4"
        const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit5:1.3.71"
        const val mockk = "io.mockk:mockk:1.9.3"
        const val assertJ = "org.assertj:assertj-core:3.15.0"

        object Jupiter {
            private const val version = "5.6.1"
            // (Required) Writing and executing Unit Tests on the JUnit5 Platform
            const val api = "org.junit.jupiter:junit-jupiter-api:$version"
            const val engine = "org.junit.jupiter:junit-jupiter-engine:$version"
            // (Optional) If you need "Parameterized Tests"
            const val params = "org.junit.jupiter:junit-jupiter-params:$version"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.1.0"
        const val googleServicesPluginClassPath = "com.google.gms:google-services:4.3.3"
        const val firebaseCrashlyticsPluginClassPath = "com.google.firebase:firebase-crashlytics-gradle:2.0.0"
        const val firebaseCore = "com.google.firebase:firebase-core:17.2.3"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.0.0"
        const val firebasePerformance = "com.google.firebase:firebase-perf:19.0.7"
        const val firebasePerformancePluginClassPath = "com.google.firebase:perf-plugin:1.3.1"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics:17.4.0"
    }

    object Kotlin {
        private const val version = "1.3.71"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.4"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val browser = "androidx.browser:browser:1.0.0"
        const val fragment = "androidx.fragment:fragment:1.0.0"

        object Test {
            const val core = "androidx.test:core:1.2.0"
            const val runner = "androidx.test:runner:1.2.0"
            const val rules = "androidx.test:rules:1.2.0"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.1.1"
        }

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        const val coreKtx = "androidx.core:core-ktx:1.2.0"

        object Lifecycle {
            private const val version = "2.2.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
        }

        object Room {
            private const val version = "2.2.5"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Navigation {
            private const val version = "2.3.0-alpha04"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }
    }

    object Dagger {
        private const val version = "2.27"
        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object Microsoft {
        const val appCenterAnalytics = "com.microsoft.appcenter:appcenter-analytics:3.1.0"
    }

    object Arrow {
        private const val version = "0.10.4"
        const val optics = "io.arrow-kt:arrow-optics:$version"
        const val syntax = "io.arrow-kt:arrow-syntax:$version"
        const val meta = "io.arrow-kt:arrow-meta:$version"
    }
}
