package com.demo.streamflix.admin.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object Helpers {
    fun formatDate(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
               "${localDateTime.monthNumber.toString().padStart(2, '0')}/" +
               "${localDateTime.year} " +
               "${localDateTime.hour.toString().padStart(2, '0')}:" +
               "${localDateTime.minute.toString().padStart(2, '0')}"
    }
    
    fun formatDateOnly(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
               "${localDateTime.monthNumber.toString().padStart(2, '0')}/" +
               "${localDateTime.year}"
    }
    
    fun truncateText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }
    
    fun getStatusBadge(isActive: Boolean): String {
        return if (isActive) {
            "✅ Ativo"
        } else {
            "❌ Inativo"
        }
    }
    
    fun getAdminBadge(isAdmin: Boolean): String {
        return if (isAdmin) {
            "👑 Admin"
        } else {
            "👤 Usuário"
        }
    }
}