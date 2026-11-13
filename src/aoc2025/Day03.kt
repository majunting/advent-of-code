package aoc2025

import AocApplication
import utils.longNumPlus
import utils.println

class Day03: AocApplication {
    override fun defaultPart1(input: List<String>): Any {
        return input.fold(0) { acc, str ->
            acc + findLargestJoltage(str)
        }
    }

    override fun defaultPart2(input: List<String>): Any {
        val joltageList = input.map { findJoltageByRemoving(it, it.length - 12) }
        return joltageList.fold("0") { acc, str ->
//            "acc: $acc".println()
//            "str: $str".println()
            acc.longNumPlus(str)
        }
    }

    private fun findLargestJoltage(input: String): Int {
        val (largest, largestIdx, largestCount) = findLargestDigit(input)
        if (largestCount > 1) return largest * 11
        return if (largestIdx == input.lastIndex) {
            val (small, _, _) = findLargestDigit(input.substring(0 ..< largestIdx))
            small * 10 + largest
        } else {
            val (small, _, _) = findLargestDigit(input.substring(largestIdx + 1))
            largest * 10 + small
        }
    }

    private fun findLargestDigit(input: String): Triple<Int, Int, Int> {
        var largest = 0
        var largestIdx = 0
        var largestCount = 0
        for (i in input.indices) {
            if (input[i].digitToInt() > largest) {
                largest = input[i].digitToInt()
                largestCount = 1
                largestIdx = i
            } else if (input[i].digitToInt() == largest) {
                largestCount++
            }
        }
        return Triple(largest, largestIdx, largestCount)
    }

    private fun findJoltageByRemoving(input: String, remove: Int): String {
        val str = StringBuilder()
        var removeCount = 0
        for (i in input.indices) {
            if (removeCount < remove) {
                if (str.isEmpty()) {
                    str.append(input[i])
                } else {
                    if (str.last().digitToInt() >= input[i].digitToInt()) {
                        str.append(input[i])
                    } else {
                        while (removeCount < remove) {
                            if (str.isEmpty()) break
                            if (str.last().digitToInt() < input[i].digitToInt()) {
                                str.deleteCharAt(str.lastIndex)
                                removeCount++
                            } else break
                        }
                        str.append(input[i])
                    }
                }
            } else str.append(input[i])
        }
        var n = 0
        while (n < 9) {
            n++
            if (removeCount >= remove) break
            for (i in 0 ..< str.length) {
                if (str[i].digitToInt() == n) {
                    str.deleteCharAt(i)
                    removeCount++
                    n--
                    break
                }
                if (removeCount >= remove) break
            }
        }
        str.toString().println()
        return str.toString()
    }
}