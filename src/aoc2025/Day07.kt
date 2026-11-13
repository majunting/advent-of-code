package aoc2025

import AocApplication
import utils.println

class Day07: AocApplication {
    var inputMap = mutableListOf<MutableList<Long>>()
    override fun defaultPart1(input: List<String>): Any {
        updateInputMap(input)
        val res = part1BeamSplit()
        return res
    }

    override fun defaultPart2(input: List<String>): Any {
        updateInputMap(input)
        val res = part2BeamSplit()
        return res
    }

    private fun updateInputMap(input: List<String>) {
        inputMap.clear()
        input.forEach { str ->
            val tmp = mutableListOf<Long>()
            str.forEach { it ->
                when(it) {
                    'S' -> tmp.add(-2)
                    '.' -> tmp.add(0)
                    '^' -> tmp.add(-1)
                }
            }
            inputMap.add(tmp)
        }
    }

    private fun part1BeamSplit(): Long {
        var count = 0L
        for (i in 1 .. inputMap.lastIndex) {
            for (j in 0 .. inputMap[i].lastIndex) {
                if (inputMap[i][j] == 0L) {
                    if (inputMap[i - 1][j] == -2L || inputMap[i - 1][j] == 1L) {
                        inputMap[i][j] = 1
                    }
                } else if (inputMap[i][j] == -1L) {
                    if (inputMap[i - 1][j] == 1L)  {
                        count++
                        try {
                            if (inputMap[i][j - 1] == 0L) inputMap[i][j - 1] = 1
                            if (inputMap[i][j + 1] == 0L) inputMap[i][j + 1] = 1
                        } catch (e: IndexOutOfBoundsException) {}
                    }
                }
            }
        }
        return count
    }

    private fun part2BeamSplit(): Long {
        for (i in 1 .. inputMap.lastIndex) {
            for (j in 0 ..inputMap[i].lastIndex) {
                if (inputMap[i][j] >= 0L) {
                    if (inputMap[i - 1][j] == -2L) inputMap[i][j] = 1L
                    else if (inputMap[i - 1][j] > 0) inputMap[i][j] += inputMap[i - 1][j]
                } else if (inputMap[i][j] == -1L) {
                    if (inputMap[i - 1][j] > 0) {
                        try {
                            if (inputMap[i][j - 1] >= 0) inputMap[i][j - 1] += inputMap[i - 1][j]
                            if (inputMap[i][j + 1] == 0L) inputMap[i][j + 1] = inputMap[i - 1][j]
                        } catch (e: IndexOutOfBoundsException) {}
                    }
                }
            }
        }
        return inputMap.last().sum()
    }
}