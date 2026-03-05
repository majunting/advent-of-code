package aoc2025

import AocApplication
import utils.allDirections
import utils.plus

class Day04: AocApplication {
    lateinit var strList: MutableList<MutableList<Char>>
    override fun defaultPart1(input: List<String>): Any {
        strList = input.map{it.toMutableList()}.toMutableList()
        return findPaper()
    }
    override fun defaultPart2(input: List<String>): Any {
        strList = input.map{it.toMutableList()}.toMutableList()
        var res = 0
        while (true) {
            val count = findPaper()
            res += count
            if (count == 0) break
        }
        return res
    }

    private fun findPaper(): Int {
        var res = 0
        for (i in strList.indices) {
            for (j in strList[i].indices) {
                if (strList[i][j] == '.') continue
                if (allDirections.map {
                        getElement(Pair(i, j).plus(it))
                    }.count { it == '@' || it == '#' } < 4) {
                    strList[i][j] = '#'
                    res++
                }
            }
        }
        strList = strList.map {
            it.map { c ->
                if (c == '#') '.' else c
            }.toMutableList()
        }.toMutableList()
        return res
    }

    private fun getElement(idx: Pair<Int, Int>): Char? =
        try {
            strList[idx.first][idx.second]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
}