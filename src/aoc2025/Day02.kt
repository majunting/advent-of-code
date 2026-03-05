package aoc2025

import AocApplication
import utils.println

class Day02: AocApplication {
    override fun defaultPart1(input: List<String>): Any {
        val ranges = input[0].split(',').map {
            it.split('-')[0].toLong() to it.split('-')[1].toLong()
        }
        var sum = 0L
        ranges.map{
            getNumbersToCheck(it).map { num ->
                if(checkPart1Invalid(num)) {
//                    num.println()
                    sum += num
                }
            }
        }
        return sum
    }

    override fun defaultPart2(input: List<String>): Any {
        val ranges = input[0].split(',').map {
            it.split('-')[0].toLong() to it.split('-')[1].toLong()
        }
        val resList = mutableListOf<Long>()
        ranges.map{
            getNumbersToCheck(it, twoParts = false).map { num ->
                if (checkPart2Invalid(num)) resList.add(num)
            }
        }
        return resList.distinct().sum()
    }

    private fun getNumbersToCheck(range: Pair<Long, Long>, twoParts: Boolean = true): List<Long> {
        return if (twoParts) {
            if (range.first.digits() == range.second.digits()) {
                if (range.first.digits() % 2 == 0) (range.first..range.second).toList()
                else listOf()
            } else {
                if (range.first.digits() % 2 == 0) (range.first..constructNum(
                    range.first.digits(),
                    false
                )).toList()
                else (constructNum(range.second.digits(), true)..range.second).toList()
            }
        } else {
            (range.first .. range.second).toList()
        }
    }

    private fun Long.digits(): Int = this.toString().length

    private fun constructNum(digits: Int, min : Boolean): Long {
        if (min) {
            if (digits == 1) return 0L
            val str = StringBuilder("1")
            repeat(digits - 1) {
                str.append("0")
            }
            return str.toString().toLong()
        } else {
            val str = StringBuilder()
            repeat(digits) {
                str.append("9")
            }
            return str.toString().toLong()
        }
    }

    private fun checkPart1Invalid(num: Long): Boolean {
        val front = num / constructNum(num.digits() / 2 + 1, true)
        val back = num % constructNum(num.digits() / 2 + 1, true)
        return front == back
    }

    private fun checkPart2Invalid(num: Long): Boolean {
        val digits = num.digits()
        for (i in 1 .. digits / 2) {
            if (digits % i == 0) {
                val str = StringBuilder()
                repeat(digits / i) {
                    str.append(num.toString().substring(0..< i))
                }
                if (str.toString().toLong() == num) return true
            }
        }
        return false
    }
}