import java.nio.file.Files
import java.nio.file.Paths

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val inputFile = Paths.get("src", "$name.txt")
    return if (Files.exists(inputFile))
        Files.readAllLines(inputFile)
            .map { it.trim() }
            .filter { it.isNotBlank() }
    else
        listOf()
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