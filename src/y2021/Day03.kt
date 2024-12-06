package y2021

import readLines
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>): Int {
        val bits = input.map { line -> line.toCharArray().map { Character.getNumericValue(it) } }
        val gammaRate = bits.getMostCommonBits()
        val epsilonRate = gammaRate.flipBinary()
        return gammaRate.getDecimal() * epsilonRate.getDecimal()
    }

    fun part2(input: List<String>): Int {
        val bits = input.map { line -> line.toCharArray().map { Character.getNumericValue(it) } }
        val oxygen = bits.filterByMostCommonBits()
        val co2 = bits.filterByLeastCommonBits()
        return oxygen.getDecimal() * co2.getDecimal()
    }

    val testInput = readLines("y2021/Day03_test")
    val input = readLines("y2021/Day03")

    println(part1(testInput))
    check(part1(testInput) == 198)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 230)
    println(part2(input))
}

private fun List<List<Int>>.filterByMostCommonBits(): List<Int> {
    if (size == 1) return this[0]
    val mostCommonBit = getMostCommonBits().first()
    return listOf(mostCommonBit) +
            filter { it.first() == mostCommonBit }
                .map { it.drop(1) }.filterByMostCommonBits()
}

private fun List<List<Int>>.filterByLeastCommonBits(): List<Int> {
    if (size == 1) return this[0]
    val leastCommonBit = getMostCommonBits().flipBinary().first()
    return listOf(leastCommonBit) +
            filter { it.first() == leastCommonBit }
                .map { it.drop(1) }.filterByLeastCommonBits()
}

private fun List<List<Int>>.getMostCommonBits() =
    this.reduce { a, b -> a.zip(b) { x, y -> x + y } }
        .map { (it.toDouble() / this.size.toDouble()).roundToInt() }

private fun List<Int>.flipBinary() = this.map { if (it == 0) 1 else 0 }

private fun List<Int>.getDecimal() =
    this.reversed()
        .mapIndexed { index, i -> 2.0.pow(index).times(i).roundToInt() }
        .sum()