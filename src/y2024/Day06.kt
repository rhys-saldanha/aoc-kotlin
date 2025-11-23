package y2024

import common.Direction
import common.Grid
import common.Point
import common.getOrNull
import common.set
import common.values
import readText

fun main() {

    fun part1(input: String): Int {
        return simulateWalk(input.toGrid())
            .flatten()
            .count { it == 'X' }
    }

    fun part2(input: String): Int {
        return 0
    }

    val testInput = readText("y2024/Day06_test")
    check(part1(testInput) == 41)
//  check(part2(testInput) == 2)

    val input = readText("y2024/Day06")
    println(part1(input))
//  println(part2(input))
}

private fun String.toGrid(): Grid<Char> = trimIndent()
    .split("\n")
    .map { it.toCharArray().toList() }

private val guardMovement: Map<Char, Direction> = mapOf(
    '^' to Direction.U,
    '>' to Direction.R,
    'v' to Direction.D,
    '<' to Direction.L,
)

private val turn: Map<Direction, Direction> = mapOf(
    Direction.U to Direction.R,
    Direction.R to Direction.D,
    Direction.D to Direction.L,
    Direction.L to Direction.U,
)

private fun <K, V> Map<K, V>.getKey(value: V) =
    entries.firstOrNull { it.value == value }!!.key

private fun Grid<Char>.findGuard(): Pair<Point, Char>? = this.values.firstOrNull { it.second in guardMovement.keys }

fun simulateStep(grid: Grid<Char>): Grid<Char>? = grid.findGuard()
    ?.let { (guardLoc, guardChar) ->
        val currentDir = guardMovement.getValue(guardChar)
        val ahead = grid.getOrNull(guardLoc.shift(currentDir))

        when (ahead) {
            null -> grid.set(guardLoc, 'X')

            '#' -> {
                val nextDir = turn.getValue(currentDir)

                grid.set(guardLoc, guardMovement.getKey(nextDir))
            }

            else -> grid
                .set(guardLoc, 'X')
                .set(guardLoc.shift(currentDir), guardChar)
        }
    }

fun simulateWalk(start: Grid<Char>) = generateSequence(start) { grid -> simulateStep(grid) }.last()
