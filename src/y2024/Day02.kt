package y2024

import mapInner
import readLines
import split
import y2024.SortDirection.ASCENDING
import y2024.SortDirection.DESCENDING
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val reports = input
            .split()
            .mapInner { it.toInt() }

        return reports.count { report -> isSafe(report) }
    }

    fun part2(input: List<String>): Int {
        val reports = input
            .split()
            .mapInner { it.toInt() }

        return reports.count { report -> isSafeDampened(report) }
    }

    val testInput = readLines("y2024/Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readLines("y2024/Day02")
    println(part1(input))
    println(part2(input))
}

fun isSafe(report: List<Int>): Boolean =
    smallSteps(report) && (isOrdered(report, ASCENDING) || isOrdered(report, DESCENDING))

fun isSafeDampened(report: List<Int>): Boolean {
    return isSafe(report) || dampened(report).any { isSafe(it) }
}

private fun smallSteps(report: List<Int>) = report.windowed(2)
    .all { (a, b) -> abs(a - b) in 1..3 }

private fun isOrdered(report: List<Int>, direction: SortDirection) = report.windowed(2)
    .all { (a, b) -> direction.check(a, b) }

enum class SortDirection {
    ASCENDING {
        override fun check(a: Int, b: Int): Boolean = a < b
    },
    DESCENDING {
        override fun check(a: Int, b: Int): Boolean = a > b
    };

    abstract fun check(a: Int, b: Int): Boolean
}

private fun dampened(report: List<Int>): List<List<Int>> =
    List(report.size) { index -> report.filterIndexed { i, _ -> i != index } }