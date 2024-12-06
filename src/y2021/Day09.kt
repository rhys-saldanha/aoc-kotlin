package y2021

import readLines
import java.util.function.Predicate

fun main() {
    fun getHeightMapFromInput(input: List<String>) = HeightMap(input.map { it.toList() }
        .mapIndexed { y, row ->
            row.mapIndexed { x, height ->
                Point(Coordinate(x, y), height.digitToInt())
            }
        }.flatten())

    fun part1(input: List<String>): Int {
        val heightMap = getHeightMapFromInput(input)

        val lowPoints = heightMap.getLowPoints()
        return lowPoints.sumOf { it.height } + lowPoints.size
    }

    fun part2(input: List<String>): Int {
        val heightMap = getHeightMapFromInput(input)

        val basins = heightMap.getLowPoints()
            .map { lowPoint -> heightMap.findArea(lowPoint) { it.height == 9 } }

        val largestBasins = basins.sortedByDescending { it.size }.take(3)

        return largestBasins.map { it.size }.reduce { acc, i -> acc * i }
    }

    val testInput = readLines("y2021/Day09_test")
    val input = readLines("y2021/Day09")

    val part1Answer = part1(testInput)
    println(part1Answer)
    check(part1Answer == 15)
    println(part1(input))

    val part2Answer = part2(testInput)
    println(part2Answer)
    check(part2Answer == 1134)
    println(part2(input))
}

fun HeightMap.getLowPoints(): List<Point> {
    return this.mapWithAdjacent { point, adjacentPoints ->
        if (adjacentPoints.all { point.height < it.height }) point else null
    }.filterNotNull()
}

//Uses y2021.Coordinate class from Day 5
data class Point(val coordinate: Coordinate, val height: Int)

data class HeightMap(val points: List<Point>) {
    private val pointsByCoordinate = points.associateBy { it.coordinate }

    fun <T> mapWithAdjacent(fn: (point: Point, adjacentPoints: List<Point>) -> T): List<T> {
        return points.map { point -> fn(point, point.adjacent()) }
    }

    fun findArea(point: Point, edge: Predicate<Point>): Set<Point> {
        val area = mutableSetOf<Point>()

        tailrec fun getArea(nextPoints: List<Point>, area: MutableSet<Point>): MutableSet<Point> {
            val insideArea = nextPoints.filter { edge.negate().test(it) }
            if (insideArea.isEmpty()) return area

            area.addAll(insideArea)

            val newAdjacentPoints = insideArea
                .flatMap { it.adjacent() }
                .filter { !area.contains(it) }

            return getArea(newAdjacentPoints, area)
        }

        return getArea(listOf(point), area)
    }

    private fun Point.adjacent(): List<Point> {
        val adjacentDirections = listOf(Coordinate::plusX, Coordinate::minusX, Coordinate::plusY, Coordinate::minusY)

        return adjacentDirections.map { it(this.coordinate) }
            .mapNotNull { pointsByCoordinate[it] }
            .toList()
    }
}