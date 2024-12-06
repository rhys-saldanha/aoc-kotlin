import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String): List<String> {
    val inputFile = Path("src", "$name.txt")

    check(inputFile.exists()) { "input file $name does not exist" }

    return inputFile.readLines()
}

fun readText(name: String): String {
    val inputFile = Path("src", "$name.txt")

    check(inputFile.exists()) { "input file $name does not exist" }

    return inputFile.readText()
}

fun String.splitChunks(): List<String> = this.split("\n\n")

fun List<String>.split(regex: Regex = "\\s+".toRegex()): List<List<String>> =
    this.map { line -> line.trim().split(regex) }

fun <T, U> List<List<T>>.mapInner(fn: (T) -> U): List<List<U>> = this.map { it.map(fn) }

fun <T> List<T>.toPair(): Pair<T, T> {
    require(this.size == 2) { "Expected list of length 2, got list of length ${this.size}" }

    return Pair(this[0], this[1])
}

/**
 * Combine the given lists into a list of lists.
 */
fun <T> zip(vararg lists: List<T>): List<List<T>> {
    return zip(*lists, transform = { it })
}

/**
 * Combine the given lists into a list of lists, applying the given transform function.
 */
fun <T, V> zip(vararg lists: List<T>, transform: (List<T>) -> V): List<V> {
    val minSize = lists.minOf(List<T>::size)
    val list = ArrayList<V>(minSize)

    val iterators = lists.map { it.iterator() }
    var i = 0
    while (i < minSize) {
        list.add(transform(iterators.map { it.next() }))
        i++
    }

    return list
}