package com.demo.streamflix.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {
    
    private val dateFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)
    
    fun formatDate(date: LocalDate): String {
        return date.format(dateFormatter)
    }
    
    fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(dateTimeFormatter)
    }
    
    fun parseDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, dateFormatter)
    }
    
    fun parseDateTime(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter)
    }
    
    fun isDateInFuture(date: LocalDate): Boolean {
        return date.isAfter(LocalDate.now())
    }
    
    fun isDateInPast(date: LocalDate): Boolean {
        return date.isBefore(LocalDate.now())
    }
    
    fun daysBetween(startDate: LocalDate, endDate: LocalDate): Int {
        return startDate.until(endDate).days
    }
    
    fun addDays(date: LocalDate, days: Int): LocalDate {
        return date.plusDays(days.toLong())
    }
}