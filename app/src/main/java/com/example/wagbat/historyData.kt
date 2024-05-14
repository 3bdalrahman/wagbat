package com.example.wagbat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orderHistory")
data class historyData(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val userId: String,
    val restName: String,
    val total: Double,
    val date: String,
    val time: String
)
