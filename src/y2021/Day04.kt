package y2021

import readInput
import zip

fun main() {
    fun getBingoDraws(input: List<String>): List<Int> =
        input[0]
            .split(",")
            .map { Integer.valueOf(it) }

    fun getBingoBoards(input: List<String>): List<BingoBoard> {
        val boards: MutableList<BingoBoard> = ArrayList()
        var rows: MutableList<List<Int>> = ArrayList()

        input.drop(2).forEach { line ->
            if (line.isBlank()) {
                boards.add(BingoBoard(rows))
                rows = ArrayList()
            } else {
                rows.add(line.trim().split("\\s+".toRegex()).map { Integer.valueOf(it) })
            }
        }

        boards.add(BingoBoard(rows))
        return boards
    }

    fun getFirstWinner(bingoDraws: List<Int>, bingoBoards: List<BingoBoard>): Pair<BingoBoard, Int> {
        bingoDraws.forEach { draw ->
            bingoBoards.forEach { it.mark(draw) }
            val winner = bingoBoards.stream()
                .filter { it.hasWon() }
                .findFirst()
            if (winner.isPresent) {
                return Pair(winner.get(), draw)
            }
        }
        throw IllegalStateException()
    }

    fun getLastWinner(bingoDraws: List<Int>, bingoBoards: List<BingoBoard>): Pair<BingoBoard, Int> {
        var _bingoBoards = bingoBoards
        bingoDraws.forEach { draw ->
            _bingoBoards.forEach { it.mark(draw) }
            _bingoBoards.forEach {
                if (it.hasWon() && _bingoBoards.size == 1) {
                    return Pair(it, draw)
                }
            }
            _bingoBoards = _bingoBoards.filter { !it.hasWon() }
        }
        throw IllegalStateException()
    }

    fun part1(input: List<String>): Int {
        val bingoDraws = getBingoDraws(input)
        val bingoBoards = getBingoBoards(input)

        val (winningBoard, winningDraw) = getFirstWinner(bingoDraws, bingoBoards)

        return winningBoard.sumUnmarked() * winningDraw
    }

    fun part2(input: List<String>): Int {
        val bingoDraws = getBingoDraws(input)
        val bingoBoards = getBingoBoards(input)

        val (winningBoard, winningDraw) = getLastWinner(bingoDraws, bingoBoards)

        return winningBoard.sumUnmarked() * winningDraw
    }

    val testInput = readInput("y2021/Day04_test")
    val input = readInput("y2021/Day04")

    println(part1(testInput))
    check(part1(testInput) == 4512)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1924)
    println(part2(input))
}

class BingoBoard constructor(numbers: List<List<Int>>) {
    private val board: List<List<BingoNumber>> = numbers.map { row ->
        row.map { BingoNumber(it) }
    }

    fun mark(number: Int) = board.forEach { row ->
        row.forEach {
            if (it.equals(number)) {
                it.mark()
            }
        }
    }

    private fun generateAllLines() =
        listOf(rows(), columns()).flatten()

    internal fun rows() = board
    internal fun columns() = zip(*board.toTypedArray())

    fun hasWon() = generateAllLines().any { line -> line.all { it.isMarked } }

    override fun toString(): String {
        return board
            .map { row -> row.joinToString(" ") }
            .joinToString(prefix = "\n", separator = "\n", postfix = "\n")
    }

    fun sumUnmarked() = board.map { row -> row.filter { !it.isMarked } }
        .flatten()
        .sumOf { it.number }
}

data class BingoNumber(val number: Int) {
    var isMarked: Boolean = false

    fun mark() {
        isMarked = true
    }

    override fun equals(other: Any?): Boolean {
        return number == other
    }

    override fun hashCode(): Int {
        return number
    }

    override fun toString(): String {
        return if (isMarked) "[$number]" else "$number"
    }
}
