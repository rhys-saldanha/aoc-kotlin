package y2022

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day04Test {

    @Nested
    inner class Contains {
        @Test
        fun `distinct assignments cannot contain each-other`() {
            val assignment1 = Assignment(1, 2)
            val assignment2 = Assignment(9, 10)

            assertFalse(assignment1.contains(assignment2))
            assertFalse(assignment2.contains(assignment1))
        }

        @Test
        fun `assignment contains itself`() {
            val assignment = Assignment(1, 10)

            assertTrue(assignment.contains(assignment))
        }

        @Test
        fun `assignment contains smaller assignment`() {
            val assignment1 = Assignment(1, 10)
            val assignment2 = Assignment(4, 5)

            assertTrue(assignment1.contains(assignment2))
        }

        @Test
        fun `assignment does not contain larger assignment`() {
            val assignment1 = Assignment(4, 5)
            val assignment2 = Assignment(1, 10)

            assertFalse(assignment1.contains(assignment2))
        }
    }

    @Nested
    inner class Overlaps {
        @Test
        fun `distinct assignments cannot overlap`() {
            val assignment1 = Assignment(1, 2)
            val assignment2 = Assignment(9, 10)

            assertFalse(assignment1.overlaps(assignment2))
            assertFalse(assignment2.overlaps(assignment1))
        }

        @Test
        fun `assignment overlaps itself`() {
            val assignment = Assignment(1, 10)

            assertTrue(assignment.overlaps(assignment))
        }

        @Test
        fun `assignment overlaps start`() {
            val assignment1 = Assignment(4, 10)
            val assignment2 = Assignment(1, 5)

            assertTrue(assignment1.overlaps(assignment2))
        }

        @Test
        fun `assignment overlaps end`() {
            val assignment1 = Assignment(1, 5)
            val assignment2 = Assignment(4, 10)

            assertTrue(assignment1.overlaps(assignment2))
        }

        @Test
        fun `contained assignment also overlaps`() {
            val assignment1 = Assignment(1, 10)
            val assignment2 = Assignment(4, 5)

            assertTrue(assignment1.overlaps(assignment2))
        }
    }
}