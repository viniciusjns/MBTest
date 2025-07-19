package com.vinicius.mbtest.core.extensions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun Double.toBRL(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}

fun String.formatToBrazilianDate(
    inputPattern: String = "yyyy-dd-MM",
    outputPattern: String = "dd/MM/yyyy"
): String {
    return try {
        val inputFormat = SimpleDateFormat(inputPattern, Locale("pt", "BR"))
        val outputFormat = SimpleDateFormat(outputPattern, Locale("pt", "BR"))
        val date = inputFormat.parse(this)
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.toMoney(): String =
    try { this.replace("[KMB]", "")
        .toDouble()
        .toBRL() + when {
        endsWith("K") -> " mil"
        endsWith("M") -> " mi"
        endsWith("B") -> " bi"
        else -> ""
    } } catch (e: Exception) { this }