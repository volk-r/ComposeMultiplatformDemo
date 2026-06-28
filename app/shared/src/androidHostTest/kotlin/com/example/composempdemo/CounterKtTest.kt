package com.example.composempdemo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CounterKtTest : AbstractCounterKtTest() {

    @Test
    fun testCountingUp() = runCountingUpTest()

    @Test
    fun testPackageName() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        assertThat(context.packageName).isEqualTo("com.example.composempdemo.app.shared.test")
    }
}
