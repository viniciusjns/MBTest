package com.vinicius.mbtest.core.extensions

import java.io.FileNotFoundException

fun String.readJsonFile(): String =
    requireNotNull(ClassLoader.getSystemResource(this)?.readText()) {
        throw FileNotFoundException("File $this not found!")
    }