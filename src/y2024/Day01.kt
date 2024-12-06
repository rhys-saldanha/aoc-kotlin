package y2024

import mapInner
import readLines
import split
import toPair
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (left, right) = input
            .split()
            .mapInner { it.toInt() }
            .map { it.toPair() }
            .unzip()

        return left.sorted().zip(right.sorted())
            .sumOf { (left, right) -> abs(left - right) }
    }

    fun part2(input: List<String>): Int {
        val (left, right) = input
            .split()
            .mapInner { it.toInt() }
            .map { it.toPair() }
            .unzip()

        return left.sumOf { leftN -> leftN * right.count { it == leftN } }
    }

    val testInput = readLines("y2024/Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readLines("y2024/Day01")
    println(part1(input))
    println(part2(input))
}
