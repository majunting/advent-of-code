package aoc2025

import AocApplication
import utils.println
import kotlin.math.max

class Day06: AocApplication {
    lateinit var numbers: Triple<List<Long>, List<Long>, List<Long>>
    lateinit var additionalNumbers: List<Long>
    lateinit var operators: List<Int>
    override fun defaultPart1(input: List<String>): Any {
        constructNumbersAndOperators(input)
        return part1()
    }

    override fun defaultPart2(input: List<String>): Any {
//        val operatorIndices = input.last().plus(" +").mapIndexed { index, ch ->
//            if (ch == '+') 0 to index
//            else if (ch == '*') 1 to index
//            else null
//        }.filterNotNull()
//        val numbersList = operatorIndices.mapIndexed { idx, it ->
//            if (idx == operatorIndices.lastIndex) {
//                val base = listOf(
//                    input[0].plus(" 0").substring(it.second),
//                    input[1].plus(" 0").substring(it.second),
//                    input[2].plus(" 0").substring(it.second),
//                )
//                if (input.size == 5) {
//                    base.plus(input[3].plus(" 0").substring(it.second))
//                } else base
//            } else {
//                val base = listOf(
//                    input[0].substring(it.second, operatorIndices[idx + 1].second - 1),
//                    input[1].substring(it.second, operatorIndices[idx + 1].second - 1),
//                    input[2].substring(it.second, operatorIndices[idx + 1].second - 1),
//                )
//                if (input.size == 5) {
//                    base.plus(input[3].substring(it.second, operatorIndices[1].second - 1))
//                } else base
//            }
//        }
//        numbersList.println()
//        val results = numbersList.zip(operatorIndices).map {
//            val numbers = createNumbers(it.first)
//            var res: Long = 0
//            if (it.second.first == 0) {
//                numbers.forEach { num ->
//                    res += num
//                }
//            } else {
//                res = 1
//                numbers.forEach { num ->
//                    res *= num
//                }
//            }
//            res
//        }
//        return results.sum()
        val maxIdx = max(max(input[0].lastIndex, input[1].lastIndex), max(input[2].lastIndex, input[3].lastIndex))
        val operatorIdx = input.lastIndex
        var res = 0L
        val tmpList = mutableListOf<Int>()
        var compute = false
        var operator = 0L
        for (i in maxIdx downTo 0) {
            if (compute) {
                res += compute(tmpList, operator)
                tmpList.clear()
                compute = false
            }
            var tmp = 0
            for (j in 0 ..< operatorIdx) {
                if (i > input[j].lastIndex || input[j][i] == ' ') continue
                if (tmp == 0) tmp = input[j][i].digitToInt()
                else tmp = tmp * 10 + input[j][i].digitToInt()
            }
            if (tmp == 0) {
                compute = true
                continue
            }
            tmpList.add(tmp)
            if (i <= input[operatorIdx].lastIndex && input[operatorIdx][i] != ' ') {
                operator = if (input[operatorIdx][i] == '+') 0L else 1L
            }
        }
        res += compute(tmpList, operator)
        return res
    }

    private fun part1(): Long {
        return if (additionalNumbers.isEmpty()) {
            numbers.first.zip(numbers.second).zip(numbers.third).zip(operators).fold(0L) { acc, nums ->
                val res = when (nums.second) {
                    0 -> {
                        nums.first.first.first + nums.first.first.second + nums.first.second
                    }

                    1 -> {
                        nums.first.first.first * nums.first.first.second * nums.first.second
                    }

                    else -> 0L
                }
                res + acc
            }
        } else {
            numbers.first.zip(numbers.second).zip(numbers.third).zip(additionalNumbers).zip(operators).fold(0L) { acc, nums ->
                val res = when (nums.second) {
                    0 -> {
                        nums.first.first.first.first + nums.first.first.first.second + nums.first.first.second + nums.first.second
                    }

                    1 -> {
                        nums.first.first.first.first * nums.first.first.first.second * nums.first.first.second * nums.first.second
                    }

                    else -> 0L
                }
                res + acc
            }
        }
    }

    private fun constructNumbersAndOperators(input: List<String>) {
        numbers = Triple(
            input[0].trim().split(Regex("\\s+")).map { it.toLong() },
            input[1].trim().split(Regex("\\s+")).map { it.toLong() },
            input[2].trim().split(Regex("\\s+")).map { it.toLong() },
            )
        if (input.size == 4) {
            operators = input[3].trim().split(Regex("\\s+")).map {
                when (it) {
                    "+" -> 0
                    "*" -> 1
                    else -> -1
                }
            }
            additionalNumbers = emptyList()
        } else {
            additionalNumbers =
                input[3].trim().split(Regex("\\s+")).map { it.toLong() }
            operators = input[4].trim().split(Regex("\\s+")).map {
                when (it) {
                    "+" -> 0
                    "*" -> 1
                    else -> -1
                }
            }

        }
    }

//    private fun createNumbers(str: List<String>): List<Int> {
//        val res = mutableListOf<Int>()
//        for (j in 0 .. str[0].lastIndex) {
//            val num = StringBuilder()
//            for (i in 0 ..< str.lastIndex) {
//                if (str[i][j] != ' ') num.append(str[i][j])
//            }
//            res.add(num.toString().toInt())
//        }
//        val num = StringBuilder()
//        var index = 0
//        for (i in 0 .. str.lastIndex) {
//
//        }
//        return res.toList()
//    }

    private fun compute(nums: List<Int>, operator: Long): Long =
        nums.fold(operator) { acc, n ->
            if (operator == 0L) {
                acc + n
            } else acc * n
        }
}