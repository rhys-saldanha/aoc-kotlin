package y2023

import common.Direction
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import common.Direction.*

class Day16Test {

    @Nested
    inner class Mirror {

        @Nested
        inner class ForwardSlash {

            private val mirror = MirrorCell('/')

            @Test
            fun `should pass light going up to right`() {
                val lightDirections = mirror.passLight(U)
                assertThat(lightDirections, contains(R))
            }

            @Test
            fun `should pass light going right to up`() {
                val lightDirections = mirror.passLight(R)
                assertThat(lightDirections, contains(U))
            }

            @Test
            fun `should pass light going down to left`() {
                val lightDirections = mirror.passLight(D)
                assertThat(lightDirections, contains(L))
            }

            @Test
            fun `should pass light going left to down`() {
                val lightDirections = mirror.passLight(L)
                assertThat(lightDirections, contains(D))
            }
        }

        @Nested
        inner class BackSlash {

            private val mirror = MirrorCell('\\')

            @Test
            fun `should pass light going up to left`() {
                val lightDirections = mirror.passLight(U)
                assertThat(lightDirections, contains(L))
            }

            @Test
            fun `should pass light going right to down`() {
                val lightDirections = mirror.passLight(R)
                assertThat(lightDirections, contains(D))
            }

            @Test
            fun `should pass light going down to right`() {
                val lightDirections = mirror.passLight(D)
                assertThat(lightDirections, contains(R))
            }

            @Test
            fun `should pass light going left to up`() {
                val lightDirections = mirror.passLight(L)
                assertThat(lightDirections, contains(U))
            }
        }
    }

    @Nested
    inner class Splitter {

        @Nested
        inner class Horizontal {

            private val splitter = SplitterCell('-')

            @Test
            fun `should pass light going up to left and right`() {
                val lightDirections = splitter.passLight(U)
                assertThat(lightDirections, contains(L, R))
            }

            @Test
            fun `should pass light going down to left and right`() {
                val lightDirections = splitter.passLight(D)
                assertThat(lightDirections, contains(L, R))
            }

            @Test
            fun `should pass light going left to left`() {
                val lightDirections = splitter.passLight(L)
                assertThat(lightDirections, contains(L))
            }

            @Test
            fun `should pass light going right to right`() {
                val lightDirections = splitter.passLight(R)
                assertThat(lightDirections, contains(R))
            }
        }

        @Nested
        inner class Vertical {

            private val splitter = SplitterCell('|')

            @Test
            fun `should pass light going up to up`() {
                val lightDirections = splitter.passLight(U)
                assertThat(lightDirections, contains(U))
            }

            @Test
            fun `should pass light going down to down`() {
                val lightDirections = splitter.passLight(D)
                assertThat(lightDirections, contains(D))
            }

            @Test
            fun `should pass light going left to up and down`() {
                val lightDirections = splitter.passLight(L)
                assertThat(lightDirections, contains(U, D))
            }

            @Test
            fun `should pass light going right to up and down`() {
                val lightDirections = splitter.passLight(R)
                assertThat(lightDirections, contains(U, D))
            }
        }
    }

    @Nested
    inner class Empty {

        private val empty = EmptyCell('.')

        @ParameterizedTest
        @EnumSource(Direction::class)
        fun `should pass light in the same direction`(direction: Direction) {
            val lightDirections = empty.passLight(direction)
            assertThat(lightDirections, contains(direction))
        }
    }
}