package y2024

import common.*
import common.Direction.*
import readInput
import javax.sql.XAConnection

fun main() {

    fun countXmas(start: Point, grid: Grid<Char>): Int {
        fun getWord(start: Point, direction: Direction, grid: Grid<Char>) =
            (1..3).runningFold(start) { acc, _ -> acc shift direction }
                .map { grid.getOrNull(it) }
                .joinToString(separator = "")

        return Direction.entries
            .count { direction -> getWord(start, direction, grid) == "XMAS" }
    }

    fun part1(input: List<String>): Int {
        val grid: Grid<Char> = input.map { it.toCharArray().asList() }
        val possibleStarts = grid.values()
            .filter { (_, value) -> value == 'X' }
            .map { (point, _) -> point }

        return possibleStarts.sumOf { start -> countXmas(start, grid) }
    }

    fun hasMAS(start: Point, grid: Grid<Char>): Boolean {
        return listOf(listOf(UL, DR), listOf(UR, DL))
            .map { diagonalPair -> diagonalPair.map { direction -> grid.getOrNull(start shift direction) } }
            .all { it.toSet() == setOf('M', 'S') }
    }

    fun part2(input: List<String>): Int {
        val grid: Grid<Char> = input.map { it.toCharArray().asList() }
        val possibleStarts = grid.values()
            .filter { (_, value) -> value == 'A' }
            .map { (point, _) -> point }

        return possibleStarts.filter { start -> hasMAS(start, grid) }.count()
    }

    val testInput = readInput("y2024/Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("y2024/Day04")
    println(part1(input))
    println(part2(input))
}
