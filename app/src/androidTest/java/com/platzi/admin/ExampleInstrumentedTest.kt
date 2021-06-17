package com.platzi.admin

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.platzi.admin", appContext.packageName)
    }

    @Test
    fun testIsPasswordValid() {
        val testPassword1 = "abcabc"
        val testPassword2 = "abcabcabca"
        val testPassword3 = "abcabcabca123"

        assertEquals(false, Utils().checkPassword(testPassword1))
        assertEquals(false, Utils().checkPassword(testPassword2))
        assertEquals(true, Utils().checkPassword(testPassword3))
    }
}
