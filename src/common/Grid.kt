package common

typealias Grid<T> = List<List<T>>

enum class Direction {
    U, UR, R, DR, D, DL, L, UL
}

/**
 * Origin is top left, x across y down
 */
data class Point(val x: Int, val y: Int) {

    infix fun shift(direction: Direction): Point {
        return when (direction) {
            Direction.U -> Point(x - 1, y)
            Direction.UR -> Point(x - 1, y + 1)
            Direction.R -> Point(x, y + 1)
            Direction.DR -> Point(x + 1, y + 1)
            Direction.D -> Point(x + 1, y)
            Direction.DL -> Point(x + 1, y - 1)
            Direction.L -> Point(x, y - 1)
            Direction.UL -> Point(x - 1, y - 1)
        }
    }

    override fun toString(): String = "($x, $y)"
}

fun <T> Grid<T>.get(point: Point) = this[point.x][point.y]

fun <T> Grid<T>.has(point: Point) = this.getOrNull(point) != null

fun <T> Grid<T>.getOrNull(point: Point): T? = this.getOrNull(point.x)?.getOrNull(point.y)

fun <T, U> Grid<T>.mapValues(transform: (T) -> U): Grid<U> = this.map { row -> row.map { transform(it) } }

val <T> Grid<T>.values: Sequence<Pair<Point, T>>
    get() = sequence {
        for (x in this@values.indices) {
            for (y in this@values[x].indices) {
                yield(Point(x, y) to this@values[x][y])
            }
        }
    }

operator fun <T> Grid<T>.set(point: Point, value: T): Grid<T> =
    this.toMutableList().apply { this[point.x].toMutableList().apply { this[point.y] = value } }