import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


typealias Input = List<String>
/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun Input.toInts() = map(String::toInt)

fun String.csvToInts() = split(",").map(String::toInt) 