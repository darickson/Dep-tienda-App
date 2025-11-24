package com.example.deptienda

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.deptienda", appContext.packageName)
    }

    @Test
    fun testAppPackageNameIsCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.deptienda", appContext.packageName)
    }

    @Test
    fun testAppHasResources() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertNotNull(appContext.resources)
    }

    @Test
    fun testAppHasPackageManager() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertNotNull(appContext.packageManager)
    }
}