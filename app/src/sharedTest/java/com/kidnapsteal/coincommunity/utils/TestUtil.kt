package com.kidnapsteal.coincommunity.utils

import com.kidnapsteal.coincommunity.data.local.entity.User
import java.util.*

object TestUtil {

    fun createRandomUsers(count: Int): List<User> {
        return (1..count).map { createRandomUser() }
    }

    fun createRandomUser(): User {
        val fName = randomWord(8)
        val lName = randomWord(8)
        val email = fName.plus(lName).plus("@mailinator.com")
        val user = User(
                userId = UUID.randomUUID().toString(),
                firstName = randomWord(8),
                lastName = randomWord(8),
                avatar = "",
                email = email,
                phoneNumber = randomNumber(12).toString(),
                notificationID = UUID.randomUUID().toString(),
                bod = Date()
        )

        return user
    }

    private fun randomWord(length: Int): String {
        return ('a'..'z').randomString(length)
    }

    private fun randomNumber(length: Int): Long {
        return (0..9).randomInt(length)
    }

    private fun ClosedRange<Char>.randomString(lenght: Int): String {
        return (1..lenght)
                .map {
                    (Random()
                            .nextInt(endInclusive.toInt() - start.toInt()) + start.toInt())
                            .toChar()
                }.joinToString("")
    }

    private fun IntRange.randomInt(lenght: Int): Long {
        return (1..lenght)
                .map {
                    (Random()
                            .nextInt(endInclusive - start) + start)

                }.joinToString("").toLong()
    }

}