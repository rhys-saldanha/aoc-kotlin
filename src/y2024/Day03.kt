package y2024

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val multiplyRegex = Regex("""mul\(([0-9]{1,3}),([0-9]{1,3})\)""")
        return input.flatMap { multiplyRegex.findAll(it) }
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val multiplyRegex = Regex("""mul\(([0-9]{1,3}),([0-9]{1,3})\)|do\(\)|don't\(\)""")
        return input.flatMap { multiplyRegex.findAll(it) }
            .fold(Pair(true, 0)) { (enabled: Boolean, sum: Int), matchResult ->
                when (matchResult.groupValues[0]) {
                    "do()" -> Pair(true, sum)
                    "don't()" -> Pair(false, sum)
                    else -> when (enabled) {
                        true -> Pair(
                            true,
                            sum + matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt()
                        )

                        false -> Pair(false, sum)
                    }
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
