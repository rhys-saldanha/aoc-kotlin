package y2023

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import y2023.LightDirection.*

class Day16Test {

    @Nested
    inner class Mirror {

        @Nested
        inner class ForwardSlash {

            private val mirror = MirrorCell('/')

            @Test
            fun `should pass light going up to right`() {
                val lightDirections = mirror.passLight(UP)
                assertThat(lightDirections, contains(RIGHT))
            }

            @Test
            fun `should pass light going right to up`() {
                val lightDirections = mirror.passLight(RIGHT)
                assertThat(lightDirections, contains(UP))
            }

            @Test
            fun `should pass light going down to left`() {
                val lightDirections = mirror.passLight(DOWN)
                assertThat(lightDirections, contains(LEFT))
            }

            @Test
            fun `should pass light going left to down`() {
                val lightDirections = mirror.passLight(LEFT)
                assertThat(lightDirections, contains(DOWN))
            }
        }

        @Nested
        inner class BackSlash {

            private val mirror = MirrorCell('\\')

            @Test
            fun `should pass light going up to left`() {
                val lightDirections = mirror.passLight(UP)
                assertThat(lightDirections, contains(LEFT))
            }

            @Test
            fun `should pass light going right to down`() {
                val lightDirections = mirror.passLight(RIGHT)
                assertThat(lightDirections, contains(DOWN))
            }

            @Test
            fun `should pass light going down to right`() {
                val lightDirections = mirror.passLight(DOWN)
                assertThat(lightDirections, contains(RIGHT))
            }

            @Test
            fun `should pass light going left to up`() {
                val lightDirections = mirror.passLight(LEFT)
                assertThat(lightDirections, contains(UP))
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
                val lightDirections = splitter.passLight(UP)
                assertThat(lightDirections, contains(LEFT, RIGHT))
            }

            @Test
            fun `should pass light going down to left and right`() {
                val lightDirections = splitter.passLight(DOWN)
                assertThat(lightDirections, contains(LEFT, RIGHT))
            }

            @Test
            fun `should pass light going left to left`() {
                val lightDirections = splitter.passLight(LEFT)
                assertThat(lightDirections, contains(LEFT))
            }

            @Test
            fun `should pass light going right to right`() {
                val lightDirections = splitter.passLight(RIGHT)
                assertThat(lightDirections, contains(RIGHT))
            }
        }

        @Nested
        inner class Vertical {

            private val splitter = SplitterCell('|')

            @Test
            fun `should pass light going up to up`() {
                val lightDirections = splitter.passLight(UP)
                assertThat(lightDirections, contains(UP))
            }

            @Test
            fun `should pass light going down to down`() {
                val lightDirections = splitter.passLight(DOWN)
                assertThat(lightDirections, contains(DOWN))
            }

            @Test
            fun `should pass light going left to up and down`() {
                val lightDirections = splitter.passLight(LEFT)
                assertThat(lightDirections, contains(UP, DOWN))
            }

            @Test
            fun `should pass light going right to up and down`() {
                val lightDirections = splitter.passLight(RIGHT)
                assertThat(lightDirections, contains(UP, DOWN))
            }
        }
    }

    @Nested
    inner class Empty {

        private val empty = EmptyCell('.')

        @ParameterizedTest
        @EnumSource(LightDirection::class)
        fun `should pass light in the same direction`(lightDirection: LightDirection) {
            val lightDirections = empty.passLight(lightDirection)
            assertThat(lightDirections, contains(lightDirection))
        }
    }
}