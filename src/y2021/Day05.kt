package y2021

import readLines
import kotlin.math.abs
import kotlin.math.max

fun main() {
    fun onlyXIsSame(it: Pair<Coordinate, Coordinate>) =
        (it.first.x == it.second.x) && (it.first.y != it.second.y)

    fun onlyYIsSame(it: Pair<Coordinate, Coordinate>) =
        (it.first.y == it.second.y) && (it.first.x != it.second.x)

    fun only45degrees(it: Pair<Coordinate, Coordinate>) =
        abs(it.first.x - it.second.x) == abs(it.first.y - it.second.y)

    fun getCoordinateLines(input: List<String>) = input.map { line ->
        val coords = line.trim().split("->")
            .map { coordinate ->
                val map = coordinate.trim().split(",")
                    .map { Integer.valueOf(it) }
                Coordinate(map[0], map[1])
            }
        Pair(coords[0], coords[1])
    }

    fun getLargestCoord(coordinateLines: List<Pair<Coordinate, Coordinate>>) =
        coordinateLines.asSequence()
            .map { listOf(it.first, it.second) }
            .flatten()
            .map { max(it.x, it.y) }
            .maxOf { it }

    fun part1(input: List<String>, print: Boolean = false): Int {
        val coordinateLines = getCoordinateLines(input)

        val size = getLargestCoord(coordinateLines)

        val ventMap = VentMap(size)

        coordinateLines.filter { onlyXIsSame(it) }
            .forEach { ventMap.addHorizontalLine(it.first, it.second, print) }
        coordinateLines.filter { onlyYIsSame(it) }
            .forEach { ventMap.addVerticalLine(it.first, it.second, print) }

        if (print) {
            println(coordinateLines)
            println(ventMap)
        }

        return ventMap.getPoints()
    }


    fun part2(input: List<String>, print: Boolean = false): Int {
        val coordinateLines = getCoordinateLines(input)

        val size = getLargestCoord(coordinateLines)

        val ventMap = VentMap(size)

        coordinateLines.filter { onlyXIsSame(it) }
            .forEach { ventMap.addHorizontalLine(it.first, it.second, print) }
        coordinateLines.filter { onlyYIsSame(it) }
            .forEach { ventMap.addVerticalLine(it.first, it.second, print) }
        coordinateLines.filter { only45degrees(it) }
            .forEach { ventMap.addDiagonalLine(it.first, it.second, print) }

        if (print) {
            println(ventMap)
        }

        return ventMap.getPoints()
    }

    val testInput = readLines("y2021/Day05_test")
    val input = readLines("y2021/Day05")

    val part1Answer = part1(testInput, print = false)
    println(part1Answer)
    check(part1Answer == 5)
    println(part1(input))

    val part2Answer = part2(testInput, print = false)
    println(part2Answer)
    check(part2Answer == 12)
    println(part2(input))
}

data class Coordinate(val x: Int, val y: Int) {
    fun plusX() = Coordinate(x + 1, y)
    fun minusX() = Coordinate(x - 1, y)
    fun plusY() = Coordinate(x, y + 1)
    fun minusY() = Coordinate(x, y - 1)

    override fun toString(): String {
        return "($x,$y)"
    }
}

private class VentMap(size: Int) {
    private val map: MutableList<MutableList<Int>> = MutableList(size + 1) { MutableList(size + 1) { 0 } }

    fun addHorizontalLine(start: Coordinate, end: Coordinate, print: Boolean = false) {
        val x = start.x
        for (y in start.y toward end.y) {
            val row = map[y]
            row[x] = row[x] + 1
            map[y] = row
        }
        if (print) {
            println(start)
            println(end)
            println(this)
        }
    }

    fun addVerticalLine(start: Coordinate, end: Coordinate, print: Boolean = false) {
        val y = start.y
        val row = map[y]
        for (x in start.x toward end.x) {
            row[x] = row[x] + 1
        }
        if (print) {
            println(start)
            println(end)
            println(this)
        }
    }

    fun addDiagonalLine(start: Coordinate, end: Coordinate, print: Boolean = false) {
        for (p in (start.x toward end.x).zip((start.y toward end.y))) {
            map[p.first][p.second] += 1
        }

        if (print) {
            println(start)
            println(end)
            println(this)
        }
    }

    override fun toString(): String {
        var string = "\n"

        for (y in 0 until map.size) {
            val row = map.getOrElse(y) { mutableListOf() }
            for (x in 0 until row.size) {
                val value = row.getOrElse(x) { 0 }
                string += if (value == 0) " ." else " $value"
            }
            string += "\n"
        }
        return string
    }

    fun getPoints(): Int = map.sumOf { row -> row.count { it > 1 } }
}

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}