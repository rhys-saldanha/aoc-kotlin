package y2023

import readInput
import y2023.LightDirection.*

fun main() {

    fun List<String>.toCellGrid(): Grid<Cell> {
        return this.map { it.toList() }
            .map { row ->
                row.map { cell ->
                    when (cell) {
                        '/' -> MirrorCell('/')
                        '\\' -> MirrorCell('\\')
                        '|' -> SplitterCell('|')
                        '-' -> SplitterCell('-')
                        '.' -> EmptyCell('.')
                        else -> throw Exception("Unknown cell type")
                    }
                }
            }
    }

    fun part1(input: List<String>): Int {
        return computeEnergy(input.toCellGrid(), Point(0, 0) pointing RIGHT)
    }

    fun part2(input: List<String>): Int {
        val grid = input.toCellGrid()
        return allStartInstructions(grid)
            .maxOf { start -> computeEnergy(grid, start) }
    }

    val testInput = readInput("y2023/Day16_test")
    println(part1(testInput))
    check(part1(testInput) == 46)
    println(part2(testInput))
    check(part2(testInput) == 51)

    val input = readInput("y2023/Day16")
    println(part1(input))
    println(part2(input))
}

enum class LightDirection {
    UP, DOWN, LEFT, RIGHT
}

interface Cell {
    fun passLight(lightDirection: LightDirection): Set<LightDirection>
}

class EmptyCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: LightDirection): Set<LightDirection> {
        return setOf(lightDirection)
    }

    override fun toString(): String = "$type"
}

class MirrorCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: LightDirection): Set<LightDirection> {
        return when (type) {
            '/' -> when (lightDirection) {
                UP -> setOf(RIGHT)
                RIGHT -> setOf(UP)
                DOWN -> setOf(LEFT)
                LEFT -> setOf(DOWN)
            }

            '\\' -> when (lightDirection) {
                UP -> setOf(LEFT)
                RIGHT -> setOf(DOWN)
                DOWN -> setOf(RIGHT)
                LEFT -> setOf(UP)
            }

            else -> throw Exception("Unknown mirror type")
        }
    }

    override fun toString(): String = "$type"
}

class SplitterCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: LightDirection): Set<LightDirection> {
        return when (type) {
            '|' -> when (lightDirection) {
                UP -> setOf(UP)
                DOWN -> setOf(DOWN)
                RIGHT -> setOf(UP, DOWN)
                LEFT -> setOf(UP, DOWN)
            }

            '-' -> when (lightDirection) {
                UP -> setOf(LEFT, RIGHT)
                DOWN -> setOf(LEFT, RIGHT)
                RIGHT -> setOf(RIGHT)
                LEFT -> setOf(LEFT)
            }

            else -> throw Exception("Unknown splitter type")
        }
    }

    override fun toString(): String = "$type"
}

fun allStartInstructions(grid: Grid<Any>): List<Instruction> {
    return grid.indices.map { i -> Point(i, 0) pointing RIGHT } +
            grid.indices.map { i -> Point(i, grid.size - 1) pointing LEFT } +
            grid[0].indices.map { i -> Point(0, i) pointing DOWN } +
            grid[0].indices.map { i -> Point(grid.size - 1, i) pointing UP }
}

fun computeEnergy(grid: Grid<Cell>, start: Instruction): Int {

    val directionHistory = grid
        .map { row -> row.map { mutableSetOf<LightDirection>() } }
        .toMutableGrid()

    val queue = mutableListOf(start)

    while (queue.isNotEmpty()) {
        val (point, direction) = queue.removeFirst()
        val cell = grid.get(point)

        directionHistory.get(point).add(direction)

        val newDirections = cell.passLight(direction)
            .filter { grid.contains(point shift it) }
            .filter { directionHistory.get(point shift it).contains(it).not() }

        if (newDirections.isEmpty()) {
            continue
        }

        queue.addAll(newDirections.map { point shift it pointing it })
    }

    return directionHistory.sumOf { row -> row.count { it.isNotEmpty() } }
}

typealias Grid<T> = List<List<T>>
typealias MutableGrid<T> = MutableList<MutableList<T>>

data class Point(val first: Int, val second: Int) {

    infix fun shift(direction: LightDirection): Point {
        return when (direction) {
            UP -> Point(first - 1, second)
            DOWN -> Point(first + 1, second)
            LEFT -> Point(first, second - 1)
            RIGHT -> Point(first, second + 1)
        }
    }

    infix fun pointing(direction: LightDirection): Instruction {
        return Instruction(this, direction)
    }

    override fun toString(): String = "($first, $second)"
}

data class Instruction(val point: Point, val direction: LightDirection) {
    override fun toString(): String = "$point $direction"
}

fun <T> Grid<Set<T>>.toMutableGrid(): MutableGrid<MutableSet<T>> {
    return this.map { row -> row.map { it.toMutableSet() }.toMutableList() }.toMutableList()
}

fun <T> Grid<T>.get(point: Point): T {
    return this[point.first][point.second]
}

fun <T> Grid<T>.contains(point: Point): Boolean {
    return this.getOrNull(point) != null
}

fun <T> Grid<T>.getOrNull(point: Point): T? {
    return this.getOrNull(point.first)?.getOrNull(point.second)
}

fun <T> MutableGrid<T>.set(point: Point, value: T) {
    this[point.first][point.second] = value
}