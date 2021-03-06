package com.aifudao.downloaddemo

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

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
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.aifudao.downloaddemo", appContext.packageName)
    }

    @Test
    fun testSid() {
        val sid = "000000000000000007207473"
        val result = Integer.parseInt(sid).toString()
        print(result)
    }
}
