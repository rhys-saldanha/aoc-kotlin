package y2022

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { line -> createPair(line) }
            .count { it.first.contains(it.second) || it.second.contains(it.first) }
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> createPair(line) }
            .count { it.first.overlaps(it.second) }
    }

    val testInput = readInput("y2022/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("y2022/Day04")
    println(part1(input))
    println(part2(input))
}

fun createPair(line: String): Pair<Assignment, Assignment> {
    val assignments = line.split(",")
        .map { assignment -> Assignment.create(assignment) }
    return Pair(assignments[0], assignments[1])
}

data class Assignment(val start: Int, val end: Int) {
    companion object {
        fun create(line: String): Assignment {
            val numbers = line.split("-")
                .map { it.toInt() }
            return Assignment(numbers[0], numbers[1])
        }
    }

    fun contains(that: Assignment): Boolean {
        return this.start <= that.start && this.end >= that.end
    }

    fun overlaps(that: Assignment): Boolean {
        return this.start <= that.end && this.end >= that.start
    }
}