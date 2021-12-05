import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class Day04Test {
    @Test
    fun `should get lines for a 3x3 board`() {
        val board = BingoBoard(
            listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9)
            )
        )

        assertThat(
            board.rows(), equalTo(
                listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9)
                )
            )
        )

        assertThat(
            board.columns(), equalTo(
                listOf(
                    listOf(1, 4, 7),
                    listOf(2, 5, 8),
                    listOf(3, 6, 9)
                )
            )
        )
    }

    @Test
    fun `should win board of 1 number`() {
        val board = BingoBoard(listOf(listOf(5)))
        assertFalse(board.hasWon())

        board.mark(5)

        assertTrue(board.hasWon())
    }

    @Test
    fun `should win 3x3 board`() {
        val board = BingoBoard(
            listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9)
            )
        )

        board.mark(4)
        board.mark(5)
        board.mark(6)

        assertTrue(board.hasWon())
    }
}