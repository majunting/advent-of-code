package aoc2025

import AocApplication
import utils.println
import kotlin.math.min
import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status

class Day10 : AocApplication {
    lateinit var signals: List<String>
    lateinit var buttons: List<List<List<Int>>>
    lateinit var signalCounts: List<List<Int>>
    val tmpMap = mutableMapOf<Long, Int>()
    val resultList = mutableListOf<Int>()
    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        return signals.mapIndexed { index, it ->
            tmpMap.clear()
            part1(
                targetVal = it.toBinaryLong(),
                digitCount = it.lastIndex,
                currVal = 0,
                buttonIdx = Pair(index, 0),
                currCount = 0
            )
        }.sum()
    }

    //    override fun defaultPart2(input: List<String>): Any =
//        signalCounts.mapIndexed { index, counts ->
//            "iteration".println()
//            part2(
//                buttonIdx = Pair(index, 0),
//                currCount = 0,
//                countList = MutableList(counts.size) { 0 }
//            )
//        }.sum()
    override fun defaultPart2(input: List<String>): Any {
        signalCounts.forEachIndexed { index, counts ->
            Context().use { ctx ->
                val opt = ctx.mkOptimize()
//                opt.MkMinimize()
                val variables = mutableListOf<IntExpr>()
                for (i in buttons[index].indices) {
                    val variable = ctx.mkIntConst(i.toString())
                    variables.add(variable)
                    opt.Add(ctx.mkGe(variable, ctx.mkInt(0)))
                }
                counts.forEachIndexed { idx, i ->
                    val varList = mutableListOf<IntExpr>()
                    for (j in variables.indices) {
                        if (buttons[index][j].contains(idx))  varList.add(variables[j])
                    }

                    val cnt = ctx.mkAdd(
                        *varList.toTypedArray()
                    )
                    opt.Add(ctx.mkEq(cnt, ctx.mkInt(i)))
                }
                val total = ctx.mkAdd(*variables.toTypedArray())
                opt.MkMinimize(total)
                when(val status = opt.Check()) {
                    Status.SATISFIABLE -> {
                        val model = opt.model
                        resultList.add(
                            variables.mapIndexed { idx, i ->
                                (model.evaluate(i, true) as IntNum).int
                            }.sum()
                        )
                    }
                    else -> {}
                }
            }
        }
        return resultList.sum()
    }

    override fun preprocess(input: List<String>) {
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
        signalCounts = input.map {
            it.split("{")[1].dropLast(1).split(",").map { s -> s.toInt() }
        }
    }

    private fun part1(targetVal: Long, currVal: Long, buttonIdx: Pair<Int, Int>, currCount: Int, digitCount: Int): Int {
        if (tmpMap.contains(targetVal) && tmpMap[targetVal]!! < currCount) return tmpMap[targetVal]!!
        if (currVal == targetVal) {
            tmpMap[currVal] = currCount
            return currCount
        }
        if (buttonIdx.second >= buttons[buttonIdx.first].size) {
            return Int.MAX_VALUE
        }
        val buttonVal = getButtonVal(buttonIdx, digitCount)
        return min(
            part1(targetVal, currVal, Pair(buttonIdx.first, buttonIdx.second + 1), currCount, digitCount),
            part1(
                targetVal,
                currVal.xor(buttonVal),
                Pair(buttonIdx.first, buttonIdx.second + 1),
                currCount + 1,
                digitCount
            ),
        )
    }

    private fun part2(buttonIdx: Pair<Int, Int>, currCount: Int, countList: MutableList<Int>): Int {
        val checkMatch = checkCountMatch(buttonIdx.first, countList)
        if (checkMatch == 1) return currCount
        if (checkMatch == -1) return Int.MAX_VALUE
        if (buttonIdx.second >= buttons[buttonIdx.first].size) {
            return Int.MAX_VALUE
        }
        val nextIdx = part2(Pair(buttonIdx.first, buttonIdx.second + 1), currCount, countList)

        buttons[buttonIdx.first][buttonIdx.second].forEach { it -> countList[it]++ }
        val currIdx = part2(buttonIdx, currCount + 1, countList)
        buttons[buttonIdx.first][buttonIdx.second].forEach { it -> countList[it]-- }
        return min(currIdx, nextIdx)
    }

    // returns 0 if not match and no overshoot
    // returns 1 if all match
    // returns -1 if overshoot
    private fun checkCountMatch(idx: Int, countList: MutableList<Int>): Int {
        val allMatch = signalCounts[idx].zip(countList).none {
            it.first != it.second
        }
        val overshoot = signalCounts[idx].zip(countList).any {
            it.first < it.second
        }
        if (allMatch) return 1
        if (overshoot) return -1
        return 0
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

    private fun getButtonVal(idx: Pair<Int, Int>, digitCount: Int): Long {
        var res = 0L
        buttons[idx.first][idx.second].forEach { it ->
            res += 1.shl(digitCount - it)
        }
        return res
    }
}