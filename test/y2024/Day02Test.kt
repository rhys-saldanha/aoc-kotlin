package y2024

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day02Test {

    @Nested
    inner class Safe {

        @Test
        fun ascending() {
            assertThat(isSafe(listOf(1, 2, 3, 4, 7)))
                .isTrue()
        }

        @Test
        fun descending() {
            assertThat(isSafe(listOf(7, 4, 3, 2, 1)))
                .isTrue()
        }
    }

    @Nested
    inner class Unsafe {

        @Test
        fun static() {
            assertThat(isSafe(listOf(1, 1, 1, 1, 1)))
                .isFalse()

            assertThat(isSafeDampened(listOf(1, 1, 1, 1, 1)))
                .isFalse()
        }

        @Test
        fun unordered() {
            assertThat(isSafe(listOf(1, 3, 5, 4, 2)))
                .isFalse()

            assertThat(isSafeDampened(listOf(1, 3, 5, 4, 2)))
                .isFalse()
        }
    }

    @Nested
    inner class Dampened {

        @Test
        fun `repeated level`() {
            assertThat(isSafeDampened(listOf(1, 2, 3, 3, 4)))
                .isTrue()
        }

        @Test
        fun `not ascending`() {
            assertThat(isSafeDampened(listOf(1, 2, 3, 5, 4)))
                .isTrue()
        }

        @Test
        fun `not descending`() {
            assertThat(isSafeDampened(listOf(4, 5, 3, 2, 1)))
                .isTrue()
        }
    }
}