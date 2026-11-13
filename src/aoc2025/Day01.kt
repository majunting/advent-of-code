package aoc2025

import AocApplication
import utils.println

class Day01 : AocApplication {
    override fun defaultPart1(input: List<String>): Any {
        var currentPos = 50
        var count = 0
        input.forEach { it ->
            val dir = it[0] == 'L' // dir == true -> minus
            val numPair = it.substring(1).toInt().cleanUp()
            if (dir) {
                currentPos -= numPair.first
            } else currentPos += numPair.first
            if (currentPos < 0) currentPos += 100
            if (currentPos >= 100) currentPos -= 100
            if (currentPos == 0) count++
        }
        return count
    }

    override fun defaultPart2(input: List<String>): Any {
        var currentPos = 50
        var count = 0
        input.forEach { it ->
            val dir = it[0] == 'L' // dir == true -> minus
            val numPair = it.substring(1).toInt().cleanUp()
            if (dir) {
                currentPos -= numPair.first
            } else currentPos += numPair.first
            count += numPair.second
            if (currentPos < 0) {
                count = if (currentPos + numPair.first == 0) count else count + 1
                currentPos += 100
            }
            if (currentPos >= 100) {
                currentPos -= 100
                count = if (currentPos == 0) count else count + 1
            }
            if (currentPos == 0) count++
        }
        return count
    }

    private fun Int.cleanUp(): Pair<Int, Int> = this % 100 to this / 100
}