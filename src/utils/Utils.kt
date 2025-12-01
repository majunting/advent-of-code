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
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

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

val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

val allDirections = directions.plus(listOf(Pair(-1, 1), Pair(1, 1), Pair(1, -1), Pair(-1, -1)))

fun chooseClass(year: Int, day: Int): AocApplication =
    when(year) {
        2024 -> when(day) {
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
        2025 -> when(day) {
            1 -> Day01()
            else -> throw IllegalArgumentException("Unknown day for $year: $day")
        }
        else -> throw IllegalArgumentException("Unknown year: $year")
    }
