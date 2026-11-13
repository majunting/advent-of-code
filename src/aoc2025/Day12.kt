package aoc2025

import AocApplication
import utils.println

class Day12: AocApplication {
    var shapeList = MutableList(6) {
        MutableList(3) { mutableListOf(false, false, false) }
    }
    lateinit var shapeCountList: List<Int>
    lateinit var requestList: List<Pair<Pair<Int, Int>, List<Int>>>

    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        var countTooLarge = 0
        var countTooSmall = 0
        for (i in 0 .. requestList.lastIndex) {
            if (checkTooLarge(i)) countTooLarge++
            else if (checkTooSmall(i)) countTooSmall++
        }
        countTooLarge.println()
        countTooSmall.println()

        // no overlapping needs to be checked. all requests are either too large or too small
        // so result is just countTooLarge
        return countTooLarge
    }

    override fun defaultPart2(input: List<String>): Any {
        preprocess(input)
        return super.defaultPart2(input)
    }

    override fun preprocess(input: List<String>) {
        val back = input.subList(30, input.size)
        for (i in 0 .. 5) {
            for (j in 0 .. 2) {
                for (k in 0 .. 2) {
                    shapeList[i][j][k] = input[1 + 5 * i + j][k] == '#'
                }
            }
        }
        shapeCountList = shapeList.map {
            it.sumOf { i -> i.count { c -> c } }
        }
        shapeCountList.println()
        requestList = back.map { it ->
            val x = it.split(':')[0].split('x')[0].toInt()
            val y = it.split(':')[0].split('x')[1].toInt()
            val lst = it.split(": ")[1].split(' ').map{i -> i.toInt()}
            Pair(Pair(x, y), lst)
        }
    }

    // request size too small, less than total count of #
    private fun checkTooSmall(idx: Int): Boolean {
        val size = requestList[idx].first.first * requestList[idx].first.second
        val minSize = requestList[idx].second.mapIndexed { index, i ->
            shapeCountList[index] * i
        }.sum()
        return size < minSize
    }

    // request size large enough to hold all shapes without overlapping (3x3)
    private fun checkTooLarge(idx: Int): Boolean {
        val size = (requestList[idx].first.first / 3) * (requestList[idx].first.second / 3)
        val count = requestList[idx].second.sum()
        return count <= size
    }
}