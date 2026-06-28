import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class GetStringLengthTest {

    @Test
    fun testGetStringLength() {
        assertThat(getStringLength("Hello, World!")).isEqualTo(13)
    }
}