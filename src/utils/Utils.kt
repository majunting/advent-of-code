package utils

import AocApplication
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.reflect.full.createInstance

fun readInput(name: String) = Path(name).readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Pair<Int, Int>.plus(b: Pair<Int, Int>) = Pair(this.first + b.first, this.second + b.second)

fun String.isNumeric(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return this.matches(regex)
}

val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

val allDirections = directions.plus(listOf(Pair(-1, 1), Pair(1, 1), Pair(1, -1), Pair(-1, -1)))

fun loadAocApplication(year: Int, day: Int): AocApplication {
    val className = "aoc$year.Day${day.toString().padStart(2, '0')}"
    val clazz = Class.forName(className).kotlin
    val instance = clazz.objectInstance ?: clazz.createInstance()
    if (instance is AocApplication) return instance
    else error("unable to load AocApplication for year: $year, day: $day")
}
