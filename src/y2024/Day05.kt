package y2024

import readText
import splitChunks
import java.util.*

fun main() {

    fun instructionsFrom(chunk: String): List<Instruction> =
        chunk.trim().split("\n")
            .map {
                it.split("|")
                    .let { (before, after) -> Instruction(before.toInt(), after.toInt()) }
            }

    fun pagesFrom(chunk: String): List<List<Int>> =
        chunk.trim().split("\n")
            .map { order ->
                order.split(",")
                    .map { it.toInt() }
            }

    fun isCorrectlyOrdered(instruction: Instruction, page: List<Int>) =
        !(page.contains(instruction.before) && page.contains(instruction.after))
                || page.indexOf(instruction.before) < page.indexOf(instruction.after)

    fun isCorrectlyOrdered(
        instructions: List<Instruction>,
        page: List<Int>
    ) = instructions.all { isCorrectlyOrdered(it, page) }

    fun part1(input: String): Int {
        val chunks = input.splitChunks()
        val instructions = instructionsFrom(chunks[0])
        val pages = pagesFrom(chunks[1])

        return pages.filter { page -> isCorrectlyOrdered(instructions, page) }
            .sumOf { it.middle() }
    }

    fun sort(page: List<Int>, instructions: List<Instruction>): List<Int> {
        val mutablePage = page.toMutableList()

        fun failedInstructions(page: List<Int>): Sequence<Instruction> = sequence<Instruction> {
            while (!isCorrectlyOrdered(instructions, mutablePage)) {
                for (instruction in instructions) {
                    if (!isCorrectlyOrdered(instruction, mutablePage)) {
                        yield(instruction)
                    }
                }
            }
        }

        failedInstructions(page).forEach { instruction ->
            Collections.swap(
                mutablePage,
                mutablePage.indexOf(instruction.before),
                mutablePage.indexOf(instruction.after)
            )
        }

        return mutablePage
    }

    fun part2(input: String): Int {
        val chunks = input.splitChunks()
        val instructions = instructionsFrom(chunks[0])
        val pages = pagesFrom(chunks[1])

        return pages.filter { page -> !isCorrectlyOrdered(instructions, page) }
            .map { page -> sort(page, instructions) }
            .sumOf { it.middle() }
    }

    val testInput = readText("y2024/Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readText("y2024/Day05")
    println(part1(input))
    println(part2(input))
}

data class Instruction(val before: Int, val after: Int)

fun <T> List<T>.middle(): T = this[size / 2]