package com.example.composempdemo

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class GetInitialsTest {

    @Test
    fun testGetInitials() {
        val fullName = "Yevgeny Prigozhin"

        assertThat(getInitials(fullName)).isEqualTo("YP")
    }
}