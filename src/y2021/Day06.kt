package y2021

import readInput

fun main() {
    fun simulateDay(fishBucket: Map.Entry<Int, Long>): Pair<Int, Long> {
        val timer = fishBucket.key
        val count = fishBucket.value
        val newTimer = if (timer == 0) 6 else timer - 1
        return newTimer to count
    }

    fun getInitialState(input: List<String>): MutableMap<Int, Long> {
        var state = input[0].trim().split(",").map { it.toInt() }
            .groupingBy { it }.eachCount().map { entry -> entry.key to entry.value.toLong() }.toMap().toMutableMap()
        return state
    }

    fun part1(input: List<String>, print: Boolean = false): Long {
        var state = getInitialState(input)

        val days = 80

        for (day in 0 until days) {
            val newFishCount = state.getOrDefault(0, 0)
            state = state.map { simulateDay(it) }
                .fold(mutableMapOf()) { map, curr ->
                    map.apply {
                        val timer = curr.first
                        val count = curr.second
                        val existingCount = map.getOrDefault(timer, 0L)
                        map[timer] = existingCount + count
                    }
                }
            state[8] = newFishCount
            if (print) {
                print("After ${day + 1}:")
                println(state)
            }
        }

        return state.values.sumOf { it }
    }


    fun part2(input: List<String>, print: Boolean = false): Long {
        var state = getInitialState(input)

        val days = 256

        for (day in 0 until days) {
            val newFishCount = state.getOrDefault(0, 0)
            state = state.map { simulateDay(it) }
                .fold(mutableMapOf()) { map, curr ->
                    map.apply {
                        val timer = curr.first
                        val count = curr.second
                        val existingCount = map.getOrDefault(timer, 0L)
                        map[timer] = existingCount + count
                    }
                }
            state[8] = newFishCount
            if (print) {
                print("After ${day + 1}:")
                println(state)
            }
        }

        return state.values.sumOf { it }
    }

    val testInput = readInput("y2021/Day06_test")
    val input = readInput("y2021/Day06")

    val part1Answer = part1(testInput, print = false)
    println(part1Answer)
    check(part1Answer == 5934L)
    println(part1(input))

    val part2Answer = part2(testInput, print = false)
    println(part2Answer)
    check(part2Answer == 26984457539)
    println(part2(input))
}