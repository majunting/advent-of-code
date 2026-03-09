package utils

import AocApplication
import aoc2024.Day09
import aoc2024.Day10
import aoc2024.Day11
import aoc2024.Day12
import aoc2024.Day13
import aoc2024.Day14
import aoc2024.Day15
import aoc2024.Day16
import aoc2024.Day17
import aoc2024.Day18
import aoc2024.Day19
import aoc2024.Day20
import aoc2024.Day21
import aoc2024.Day22
import aoc2024.Day23
import aoc2024.Day24
import aoc2024.Day25
import aoc2025.Day01
import aoc2025.Day02
import aoc2025.Day03
import aoc2025.Day04
import aoc2025.Day05
import aoc2025.Day06
import aoc2025.Day07
import aoc2025.Day08
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.sqrt

fun readInput(year: Int, name: String) = Path("src/aoc$year/resources/$name.txt").readText().trim().lines()

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

data class Point3D(
    val x: Double,
    val y: Double,
    val z: Double,
)

data class Point(
    val x: Long,
    val y: Long,
)

fun distance3D(p1: Point3D, p2: Point3D): Double =
    sqrt((p1.x - p2.x).times(p1.x - p2.x) + (p1.y - p2.y).times(p1.y - p2.y) + (p1.z - p2.z).times(p1.z - p2.z))

fun String.longNumPlus(str: String): String {
    val res = StringBuilder()
    var i = 1
    var carry = 0
    while (i <= this.length || i <= str.length) {
        val idx1 = this.length - i
        val idx2 = str.length - i
        val num1 = if (idx1 < 0) 0 else this[idx1].digitToInt()
        val num2 = if (idx2 < 0) 0 else str[idx2].digitToInt()
        val sum = num1 + num2 + carry
        res.insert(0, sum % 10)
        carry = if (sum >= 10) 1 else 0
        i++
    }
    if (carry == 1) res.insert(0, 1)
    return res.toString()
}

val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

val allDirections = directions.plus(listOf(Pair(-1, 1), Pair(1, 1), Pair(1, -1), Pair(-1, -1)))

fun chooseClass(year: Int, day: Int): AocApplication =
    when (year) {
        2024 -> when (day) {
            9 -> Day09()
            10 -> Day10()
            11 -> Day11()
            12 -> Day12()
            13 -> Day13()
            14 -> Day14()
            15 -> Day15()
            16 -> Day16()
            17 -> Day17()
            18 -> Day18()
            19 -> Day19()
            20 -> Day20()
            21 -> Day21()
            22 -> Day22()
            23 -> Day23()
            24 -> Day24()
            25 -> Day25()
            else -> throw IllegalArgumentException("Unknown day for $year: $day")
        }

        2025 -> when (day) {
            1 -> Day01()
            2 -> Day02()
            3 -> Day03()
            4 -> Day04()
            5 -> Day05()
            6 -> Day06()
            7 -> Day07()
            8 -> Day08()
            9 -> aoc2025.Day09()
            10 -> aoc2025.Day10()
            11 -> aoc2025.Day11()
            12 -> aoc2025.Day12()
            else -> throw IllegalArgumentException("Unknown day for $year: $day")
        }

        else -> throw IllegalArgumentException("Unknown year: $year")
    }
