package com.example.happyplace.feature_happy_place.happy_place_domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString()
}