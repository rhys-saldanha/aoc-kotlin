package y2021

import readLines
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>, print: Boolean = false): Int {
        val subs = input[0].trim().split(",").map { it.toInt() }

        val median = subs.median()
        if (print) println("Median: $median")

        return subs.sumOf { abs(it - median) }
    }

    fun calculatePart2Cost(subs: List<Int>, mean: Int, print: Boolean): Int {
        val costs = subs.map { abs(it - mean) }.map { (it * (it + 1)) / 2 }
        if (print) println("Total: $costs")

        return costs.sum()
    }

    fun part2(input: List<String>, print: Boolean = false): Int {
        val subs = input[0].trim().split(",").map { it.toInt() }

        val average = subs.average()
        if (print) println("Mean: $average")

        return minOf(
            calculatePart2Cost(subs, floor(average).roundToInt(), print),
            calculatePart2Cost(subs, ceil(average).roundToInt(), print)
        )
    }

    val testInput = readLines("y2021/Day07_test")
    val input = readLines("y2021/Day07")

    val part1Answer = part1(testInput, print = true)
    println(part1Answer)
    check(part1Answer == 37)
    println(part1(input, print = true))

    val part2Answer = part2(testInput, print = true)
    println(part2Answer)
    check(part2Answer == 168)
    println(part2(input, print = true))
}

private fun Iterable<Int>.median(): Int = this.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }