package y2022

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class Day03Test {
    @Test
    fun `should get type priority`() {
        var expectedPriority = 1
        for (c in 'a'..'z') {
            assertThat(c.priority(), equalTo(expectedPriority))
            expectedPriority++
        }

        expectedPriority = 27
        for (c in 'A'..'Z') {
            assertThat(c.priority(), equalTo(expectedPriority))
            expectedPriority++
        }
    }

    @Test
    fun `should return compartments`() {
        val compartments = "aaabbb".toList()
            .toCompartments()

        assertThat(compartments, equalTo(Pair("aaa".toSet(), "bbb".toSet())))
    }
}