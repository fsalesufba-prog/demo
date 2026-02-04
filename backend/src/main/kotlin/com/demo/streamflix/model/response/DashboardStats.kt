package com.demo.streamflix.model.response

import java.time.LocalDateTime

data class DashboardStats(
    val totalUsers: Long = 0,
    val activeUsers: Long = 0,
    val totalChannels: Long = 0,
    val totalCategories: Long = 0,
    val activeSubscriptions: Long = 0,
    val newUsersLast30Days: Long = 0,
    val newChannelsLast30Days: Long = 0,
    val newSubscriptionsLast30Days: Long = 0,
    val todayLogins: Long = 0,
    val lastUpdated: LocalDateTime = LocalDateTime.now()
)