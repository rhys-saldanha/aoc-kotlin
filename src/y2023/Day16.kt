package y2023

import common.*
import common.Direction.*
import readLines
import java.lang.IllegalStateException

fun main() {

    fun List<String>.toCellGrid(): Grid<Cell> {
        return this.map { it.toList() }
            .mapValues { cell ->
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

    fun part1(input: List<String>): Int {
        return computeEnergy(input.toCellGrid(), Point(0, 0) pointing R)
    }

    fun part2(input: List<String>): Int {
        val grid = input.toCellGrid()
        return allStartInstructions(grid)
            .maxOf { start -> computeEnergy(grid, start) }
    }

    val testInput = readLines("y2023/Day16_test")
    println(part1(testInput))
    check(part1(testInput) == 46)
    println(part2(testInput))
    check(part2(testInput) == 51)

    val input = readLines("y2023/Day16")
    println(part1(input))
    println(part2(input))
}

interface Cell {
    fun passLight(lightDirection: Direction): Set<Direction>
}

class EmptyCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: Direction): Set<Direction> {
        return setOf(lightDirection)
    }

    override fun toString(): String = "$type"
}

class MirrorCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: Direction): Set<Direction> {
        return when (type) {
            '/' -> when (lightDirection) {
                U -> setOf(R)
                R -> setOf(U)
                D -> setOf(L)
                L -> setOf(D)
                else -> throw IllegalStateException("Cannot bounce light diagonally")
            }

            '\\' -> when (lightDirection) {
                U -> setOf(L)
                R -> setOf(D)
                D -> setOf(R)
                L -> setOf(U)
                else -> throw IllegalStateException("Cannot bounce light diagonally")
            }

            else -> throw Exception("Unknown mirror type")
        }
    }

    override fun toString(): String = "$type"
}

class SplitterCell(private val type: Char) : Cell {
    override fun passLight(lightDirection: Direction): Set<Direction> {
        return when (type) {
            '|' -> when (lightDirection) {
                U -> setOf(U)
                D -> setOf(D)
                R -> setOf(U, D)
                L -> setOf(U, D)
                else -> throw IllegalStateException("Cannot bounce light diagonally")
            }

            '-' -> when (lightDirection) {
                U -> setOf(L, R)
                D -> setOf(L, R)
                R -> setOf(R)
                L -> setOf(L)
                else -> throw IllegalStateException("Cannot bounce light diagonally")
            }

            else -> throw Exception("Unknown splitter type")
        }
    }

    override fun toString(): String = "$type"
}

fun allStartInstructions(grid: Grid<Any>): List<Instruction> {
    return grid.indices.map { i -> Point(i, 0) pointing R } +
            grid.indices.map { i -> Point(i, grid.size - 1) pointing L } +
            grid[0].indices.map { i -> Point(0, i) pointing D } +
            grid[0].indices.map { i -> Point(grid.size - 1, i) pointing U }
}

fun computeEnergy(grid: Grid<Cell>, start: Instruction): Int {

    val directionHistory = grid
        .mapValues { mutableSetOf<Direction>() }
        .toMutableGrid()

    val queue = mutableListOf(start)

    while (queue.isNotEmpty()) {
        val (point, direction) = queue.removeFirst()
        val cell = grid.get(point)

        directionHistory.get(point).add(direction)

        val newDirections = cell.passLight(direction)
            .filter { grid.has(point shift it) }
            .filter { directionHistory.get(point shift it).contains(it).not() }

        if (newDirections.isEmpty()) {
            continue
        }

        queue.addAll(newDirections.map { point shift it pointing it })
    }

    return directionHistory.sumOf { row -> row.count { it.isNotEmpty() } }
}

fun <T> Grid<Set<T>>.toMutableGrid(): MutableGrid<MutableSet<T>> =
    this.map { row -> row.map { it.toMutableSet() }.toMutableList() }.toMutableList()

data class Instruction(val point: Point, val direction: Direction) {
    override fun toString(): String = "$point $direction"
}

infix fun Point.pointing(direction: Direction) = Instruction(this, direction)