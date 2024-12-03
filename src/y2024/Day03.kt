package y2024

import readInput

fun main() {
    val multiplyRegex = """mul\(([0-9]{1,3}),([0-9]{1,3})\)""".toRegex()
    val doRegex = """do\(\)""".toRegex()
    val dontRegex = """don't\(\)""".toRegex()

    fun part1(input: List<String>): Int {
        return input.flatMap { multiplyRegex.findAll(it) }
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: List<String>): Int {
        return input.flatMap { """$multiplyRegex|$doRegex|$dontRegex""".toRegex().findAll(it) }
            .fold(Pair(true, 0)) { (enabled: Boolean, sum: Int), matchResult ->
                val instruction = matchResult.groupValues[0]
                when {
                    instruction == "do()" -> Pair(true, sum)
                    instruction == "don't()" || !enabled -> Pair(false, sum)
                    else -> Pair(true, sum + matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt())
                }
            }.second
    }

    val testInput = readInput("y2024/Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("y2024/Day03")
    println(part1(input))
    println(part2(input))
}
