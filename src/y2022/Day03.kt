package y2022

import readLines
import toPair

fun main() {

    fun part1(input: List<String>): Int = input.sumOf { rucksack ->
        rucksack.toList()
            .toCompartments()
            .let { compartments -> compartments.first.intersect(compartments.second) }
            .sumOf { it.priority() }
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { group ->
        group.map {
            it.toSet()
        }.let {
            it[0].intersect(it[1]).intersect(it[2])
        }.sumOf { it.priority() }
    }

    val testInput = readLines("y2022/Day03_test")
    val input = readLines("y2022/Day03")

    println(part1(testInput))
    check(part1(testInput) == 157)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 70)
    println(part2(input))
}

fun Char.priority(): Int {
    if (this.code > 96) {
        return this.code - 96
    }
    return this.code - 38
}

fun List<Char>.toCompartments(): Pair<Set<Char>, Set<Char>> = this.splitInHalf()
    .map { it.toSet() }
    .toPair()

fun <T> List<T>.splitInHalf(): List<List<T>> = this.withIndex()
    .partition { it.index < this.size / 2 }
    .toList()
    .map { it.removeIndex() }

fun <T> List<IndexedValue<T>>.removeIndex(): List<T> = this.map { it.value }