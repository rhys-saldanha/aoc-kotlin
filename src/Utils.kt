import java.nio.file.Files
import java.nio.file.Paths
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Files.readAllLines(Paths.get("src", "$name.txt"))