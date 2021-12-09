fun main() {
    fun part1(input: List<String>, print: Boolean = false): Int {
        return input.map { it.split("|")[1].trim().split(" ") }
            .flatten()
            .count { it.length in listOf(2, 3, 4, 7) }
    }

    fun part2(input: List<String>, print: Boolean = false): Int {
        val lines = getLines(input)

        return lines.sumOf { getOutputNumber(it) }
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    val part1Answer = part1(testInput)
    println(part1Answer)
    check(part1Answer == 26)
    println(part1(input))

    val part2Answer = part2(testInput)
    println(part2Answer)
    check(part2Answer == 61229)
    println(part2(input))
}

fun getLines(input: List<String>): List<List<List<Set<Char>>>> {
    val lines = input.map { line ->
        line.split("|").map {
            it.trim().split(" ").map { digit ->
                digit.toCharArray().toSet()
            }
        }
    }
    return lines
}

fun getOutputNumber(line: List<List<Set<Char>>>): Int {
    val one = line[0].find { it.size == 2 }.orEmpty()
    val four = line[0].find { it.size == 4 }.orEmpty()
    val seven = line[0].find { it.size == 3 }.orEmpty()
    val eight = line[0].find { it.size == 7 }.orEmpty()

    val commonSegments = eight.filter { !four.contains(it) }

    val six = line[0].find { it.size == 6 && !it.containsAll(one) }.orEmpty()
    val zero = line[0].find { it.size == 6 && it.containsAll(commonSegments) && it != six }.orEmpty()
    val nine = line[0].find { it.size == 6 && it != six && it != zero }.orEmpty()

    val three = line[0].find { it.size == 5 && it.containsAll(one) }.orEmpty()
    val two = line[0].find { it.size == 5 && it.containsAll(commonSegments) && it != three }.orEmpty()
    val five = line[0].find { it.size == 5 && it != three && it != two }.orEmpty()

    val segmentsToNumber = mapOf(
        zero to 0,
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9,
    )

    return line[1].map { segmentsToNumber[it] }
        .joinToString(separator = "") { it.toString() }
        .toInt()
}