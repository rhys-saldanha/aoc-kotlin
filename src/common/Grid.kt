package common

import y2023.Instruction

typealias Grid<T> = List<List<T>>
typealias MutableGrid<T> = MutableList<MutableList<T>>

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class Point(val first: Int, val second: Int) {

    infix fun shift(direction: Direction): Point {
        return when (direction) {
            Direction.UP -> Point(first - 1, second)
            Direction.DOWN -> Point(first + 1, second)
            Direction.LEFT -> Point(first, second - 1)
            Direction.RIGHT -> Point(first, second + 1)
        }
    }

    infix fun pointing(direction: Direction): Instruction {
        return Instruction(this, direction)
    }

    override fun toString(): String = "($first, $second)"
}

fun <T> Grid<Set<T>>.toMutableGrid(): MutableGrid<MutableSet<T>> {
    return this.map { row -> row.map { it.toMutableSet() }.toMutableList() }.toMutableList()
}

fun <T> Grid<T>.get(point: Point): T {
    return this[point.first][point.second]
}

fun <T> Grid<T>.has(point: Point): Boolean {
    return this.getOrNull(point) != null
}

fun <T> Grid<T>.getOrNull(point: Point): T? {
    return this.getOrNull(point.first)?.getOrNull(point.second)
}

fun <T, U> Grid<T>.mapValues(transform: (T) -> U): Grid<U> {
    return this.map { row -> row.map { transform(it) } }
}


fun <T> MutableGrid<T>.set(point: Point, value: T) {
    this[point.first][point.second] = value
}