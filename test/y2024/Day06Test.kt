package y2024

import common.Grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day06Test {

  @Nested
  inner class Move {

    @Test
    fun up() {
      val grid: Grid<Char>? = """
      . . .
      . ^ .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . ^ .
      . X .
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun down() {
      val grid: Grid<Char>? = """
      . . .
      . v .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      . X .
      . v .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun left() {
      val grid: Grid<Char>? = """
      . . .
      . < .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      < X .
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun right() {
      val grid: Grid<Char>? = """
      . . .
      . > .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      . X >
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }
  }

  @Nested
  inner class Wall {

    @Test
    fun `hit wall up`() {
      val grid: Grid<Char>? = """
      . # .
      . ^ .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . # .
      . > .
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `hit wall down`() {
      val grid: Grid<Char>? = """
      . . .
      . v .
      . # .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      . < .
      . # .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `hit wall right`() {
      val grid: Grid<Char>? = """
      . . .
      . > #
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      . v #
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `hit wall left`() {
      val grid: Grid<Char>? = """
      . . .
      # < .
      . . .
      """.toGrid()
        .let { simulateStep(it) }

      val expected: Grid<Char> = """
      . . .
      # ^ .
      . . .
      """.toGrid()

      assertThat(grid).isEqualTo(expected)
    }
  }

  @Nested
  inner class Leave {

    @Test
    fun `leave up`() {
      val grid: Grid<Char>? = simulateStep("^".toGrid())
      val expected: Grid<Char> = "X".toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `leave down`() {
      val grid: Grid<Char>? = simulateStep("v".toGrid())
      val expected: Grid<Char> = "X".toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `leave left`() {
      val grid: Grid<Char>? = simulateStep("<".toGrid())
      val expected: Grid<Char> = "X".toGrid()

      assertThat(grid).isEqualTo(expected)
    }

    @Test
    fun `leave right`() {
      val grid: Grid<Char>? = simulateStep(">".toGrid())
      val expected: Grid<Char> = "X".toGrid()

      assertThat(grid).isEqualTo(expected)
    }
  }

  @Test
  fun end() {
    val grid: Grid<Char>? = simulateStep("X".toGrid())

    assertThat(grid).isNull()
  }

  @Test
  fun `can simuate walk`() {
    val grid: Grid<Char>? = """
    . # .
    # < #
    . . .
    """.toGrid()
      .let { simulateWalk(it) }

    val expected: Grid<Char> = """
    . # .
    # X #
    . X .
    """.toGrid()

    assertThat(grid).isEqualTo(expected)
  }

  private fun String.toGrid(): Grid<Char> = trimIndent()
    .split("\n")
    .map { line ->
      line.split(" ")
        .map { it.first() }
    }
}