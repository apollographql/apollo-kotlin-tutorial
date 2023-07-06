package com.example.rocketreserver

import com.example.rocketreserver.test.BookTripMutation_TestBuilder
import com.example.rocketreserver.test.BookTripMutation_TestBuilder.Data
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val data = BookTripMutation.Data {
            bookTrips = bookTrips {
                message = ""
            }
        }
        println(data)
    }
}
