package y2021

import readLines

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readLines("y2021/Day01_test")
    check(part1(testInput) == 1)

    val input = readLines("y2021/Day01")
    println(part1(input))
    println(part2(input))
}
