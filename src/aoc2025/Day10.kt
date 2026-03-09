package aoc2025

import AocApplication
import utils.println

class Day10: AocApplication {
    lateinit var signals: List<String>
    lateinit var buttons: List<List<List<Int>>>
    lateinit var curlyBraces: List<List<Int>>
    val tmpMap = mutableMapOf<Int, Int>()

    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        signals.println()
        buttons.println()
        curlyBraces.println()
        return signals.sumOf { it ->
            tmpMap.clear()
            part1(
                targetVal = it.toBinaryLong(),
                currVal = 0,
                buttonIdx = 0,
                currCount = 0
            )
        }
//        return super.defaultPart1(input)
    }

    private fun preprocess(input: List<String>) {
        signals = input.map { it ->
            it.split("]")[0].dropWhile { chr ->
                chr == '['
            }
        }
        buttons = input.map { it ->
            it.split("]")[1].split("(").dropWhile { str -> str == " " }.map { str ->
                str.split(")")[0].split(",").map { s -> s.toInt() }
            }
        }
        curlyBraces = input.map {
            it.split("{")[1].dropLast(1).split(",").map{s -> s.toInt()}
        }
    }

    private fun part1(targetVal: Long, currVal: Long, buttonIdx: Int, currCount: Int): Int {
        if (currVal == targetVal) return currCount
    }

    private fun String.toBinaryLong(): Long {
        var res = 0L
        var curr = 1L
        for (i in this.lastIndex downTo 0) {
            if (this[i] == '#') res += curr
            curr *= 2
        }
        return res
    }
}