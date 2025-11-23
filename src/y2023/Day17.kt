package y2023

import common.Direction
import common.Direction.D
import common.Direction.L
import common.Direction.R
import common.Direction.U
import common.Direction.entries
import common.Grid
import common.Point
import common.get
import common.has
import common.mapValues
import readLines
import java.util.*

fun main() {

    fun findPath(input: List<String>, minimumRepeats: Int, maximumRepeats: Int): Int {
        val grid: Grid<Int> = input.map { it.toList() }.mapValues { it.digitToInt() }
        val queue = PriorityQueue<State>(compareBy { it.heatLoss })
        val visited = mutableSetOf<Visited>()
        val goal = Point(grid.size - 1, grid[0].size - 1)

        queue.add(State(Point(0, 0)))

        fun makeStep(state: State, direction: Direction) {
            val nextPoint = state.point shift direction
            if (!grid.has(nextPoint)) return

            val nextHeatLoss = grid.get(nextPoint)
            val nextState = State(
                nextPoint,
                direction,
                state.heatLoss + nextHeatLoss,
                if (direction == state.lastDirection) state.repeat + 1 else 1,
                state
            )

            if (nextState.repeat > maximumRepeats) return

            queue.add(nextState)
        }

        while (queue.isNotEmpty()) {
            val state = queue.poll()
            if (state.point == goal) {
                println(state.path())
                return state.heatLoss
            }
            if (!visited.add(state.visit())) continue

            if (state.lastDirection != null && state.repeat < minimumRepeats) {
                makeStep(state, state.lastDirection)
            } else for (direction in next(state.lastDirection)) {
                makeStep(state, direction)
            }
        }
        throw Exception("No path found")
    }

    fun part1(input: List<String>): Int = findPath(input, 0, 3)

    fun part2(input: List<String>): Int = findPath(input, 4, 10)

    val testInput = readLines("y2023/Day17_test")
    println(part1(testInput))
    check(part1(testInput) == 102)
    println(part2(testInput))
    check(part2(testInput) == 94)

    val input = readLines("y2023/Day17")
    println(part1(input))
    println(part2(input))
}

fun next(previous: Direction?): Array<Direction> = when (previous) {
    U -> arrayOf(U, R, L)
    R -> arrayOf(U, R, D)
    D -> arrayOf(D, R, L)
    L -> arrayOf(U, D, L)
    null -> entries.toTypedArray()
    else -> throw IllegalStateException("Cannot path diagonally")
}


data class State(
    val point: Point,
    val lastDirection: Direction? = null,
    val heatLoss: Int = 0,
    val repeat: Int = 1,
    val previous: State? = null
) {
    fun visit(): Visited {
        return Visited(point, lastDirection, repeat)
    }

    fun path(): String {
        return if (previous == null) {
            ""
        } else {
            "${previous.path()} $lastDirection"
        }
    }
}

data class Visited(val point: Point, val lastDirection: Direction?, val repeat: Int)