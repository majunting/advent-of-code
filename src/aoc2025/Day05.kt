package aoc2025

import AocApplication
import utils.println

class Day05: AocApplication {
    lateinit var ranges: List<Pair<Long, Long>>
    lateinit var values: List<Long>
    override fun defaultPart1(input: List<String>): Any {
        ranges = input.filter { it.contains("-") }.map { it.split("-")[0].toLong() to it.split("-")[1].toLong() }
        values = input.filterNot { it.contains("-") }.filter { it.length > 0 }.map { it.toLong() }
        return checkFresh()
    }

    override fun defaultPart2(input: List<String>): Any {
        ranges = input.filter { it.contains("-") }.map { it.split("-")[0].toLong() to it.split("-")[1].toLong() }
        values = input.filterNot { it.contains("-") }.filter { it.length > 0 }.map { it.toLong() }
        return findAllFresh()
    }

    private fun checkFresh(): Long {
        return values.count { i ->
            ranges.any { it.first <= i && it.second >= i }
        }.toLong()
    }

    private fun findAllFresh(): Long {
        val sorted = ranges.sortedBy { it.first }
        val mergedRanges = mutableListOf<Pair<Long, Long>>()
        var current = sorted.first()
        for (i in 1 until sorted.size) {
            if (current.second >= sorted[i].first) {
                if (current.second < sorted[i].second) { current = Pair(current.first, sorted[i].second)}
            } else {
                mergedRanges.add(current)
                current = sorted[i]
            }
        }
        mergedRanges.add(current)
        return mergedRanges.sumOf { it.second - it.first + 1 }
//        var ingredientList = mutableListOf<Long>()
//        for (range in ranges) {
//            ingredientList = ingredientList.plus((range.first .. range.second).toMutableList()).distinct().toMutableList()
//        }
//        return ingredientList.count().toLong()
    }
}