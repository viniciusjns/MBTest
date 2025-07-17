package com.vinicius.mbtest.buildsrc

import org.gradle.api.JavaVersion

object Config {
    const val APPLICATION_ID = "com.vinicius.mbtest"
    const val NAMESPACE = APPLICATION_ID
    const val COMPILE_SDK = 35
    const val MIN_SDK = 24
    const val TARGET_SDK = 35
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val JAVA_VERSION = JavaVersion.VERSION_17
    const val JVM_TARGET_VERSION = "17"
}