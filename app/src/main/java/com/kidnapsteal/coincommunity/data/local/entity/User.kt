package com.kidnapsteal.coincommunity.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(
        @PrimaryKey @ColumnInfo(name = "id")
        var userId: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var avatar: String = "",
        var email: String = "",
        var phoneNumber: String = "",
        var notificationID: String = "",
        var bod: Date = Date()
) {
    fun isEmpty(): Boolean {
        return userId.isEmpty()
    }
}