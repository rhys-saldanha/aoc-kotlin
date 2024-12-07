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

fun <T> Grid<T>.get(point: Point): T {
    return this[point.x][point.y]
}

fun <T> Grid<T>.has(point: Point): Boolean {
    return this.getOrNull(point) != null
}

fun <T> Grid<T>.getOrNull(point: Point): T? {
    return this.getOrNull(point.x)?.getOrNull(point.y)
}

fun <T, U> Grid<T>.mapValues(transform: (T) -> U): Grid<U> {
    return this.map { row -> row.map { transform(it) } }
}

fun <T> Grid<T>.values(): Sequence<Pair<Point, T>> = sequence {
    for (x in this@values.indices) {
        for (y in this@values[x].indices) {
            yield(Point(x, y) to this@values[x][y])
        }
    }
}
typealias MutableGrid<T> = MutableList<MutableList<T>>

fun <T> Grid<T>.toMutableGrid(): MutableGrid<T> =
    this.map { row -> row.toMutableList() }.toMutableList()

fun <T> MutableGrid<T>.set(point: Point, value: T): MutableGrid<T> {
    this[point.x][point.y] = value
    return this;
}