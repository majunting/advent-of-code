package aoc2025

import AocApplication
import utils.Point
import utils.println
import kotlin.collections.MutableList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day09 : AocApplication {
    lateinit var points: MutableList<Point>
    lateinit var matrix: MutableList<MutableList<Int>>
    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        var max = 0L
        for (i in 0..points.lastIndex) {
            for (j in i + 1..points.lastIndex) {
                val area = (abs(points[i].x - points[j].x) + 1) * (abs(points[i].y - points[j].y) + 1)
                max = max(area, max)
            }
        }
        return max
    }

    override fun defaultPart2(input: List<String>): Any {
        preprocess(input)
        markGreen()
        var max = 0L
        for (i in 0..points.lastIndex) {
            for (j in i + 1..points.lastIndex) {
                markColorFor2Points(i, j, false)
                val isValid = checkValidRectangle(i, j)
                if (isValid) {
                    max = max(max, matrix.sumOf {
                        it.count { n ->
                            n == 3
                        }
                    }.toLong())
                }
//                "${points[i].x}, ${points[i].y} - ${points[j].x}, ${points[j].y}".println()
//                matrix.forEach { it.println() }
//                "-----------".println()
                resetColor()
            }
        }
        return max
    }

    private fun preprocess(input: List<String>) {
        points = mutableListOf()
        input.forEach {
            points.add(
                Point(
                    (it.split(",")[0]).toLong(),
                    (it.split(",")[1]).toLong(),
                )
            )
        }
        val minX = points.minOf{it.x}
        val minY = points.minOf{it.y}
        val maxX = points.maxOf{it.x}
        val maxY = points.maxOf{it.y}
        val diffX = maxX - minX
        val diffY = maxY - minY
        for (i in 0 .. points.lastIndex) {
            points[i] = Point(points[i].x - minX + 1, points[i].y - minY + 1)
        }
        diffX.println()
        diffY.println()
//        matrix = MutableList(diffX.toInt() + 2) { MutableList(diffY.toInt() + 2) { 0 } }
        matrix = mutableListOf()
        for (i in 0 .. diffX + 1) {
            val lst = mutableListOf<Int>()
            for (j in 0 .. diffY + 1) lst.add(0)
            matrix.add(lst)
        }
    }

    private fun markGreen() {
        // mark all points
        for (i in 0..< points.lastIndex) {
            markColorFor2Points(i, i + 1, true)
        }
        markColorFor2Points(0, points.lastIndex, true)
        floodMatrix()
//        matrix.forEach { it.println() }
//        "-----------".println()
//        for (i in 0..points.lastIndex) {
//            for (j in i + 1..points.lastIndex) {
//                markColorFor2Points(i, j, true)
//                matrix.forEach { it.println() }
//                "-----------".println()
//            }
//        }
    }

    private fun markColorFor2Points(i: Int, j: Int, init: Boolean) {
        for (x in min(points[i].x, points[j].x).toInt()..max(points[i].x, points[j].x).toInt()) {
            for (y in min(points[i].y, points[j].y).toInt()..max(points[i].y, points[j].y).toInt()) {
                if (init) matrix[x][y] = 1
                else if (matrix[x][y] == 1) matrix[x][y] = 3
            }
        }
    }

    private fun resetColor() {
        for (i in 0..matrix.lastIndex) {
            for (j in 0..matrix[0].lastIndex) {
                if (matrix[i][j] > 1) matrix[i][j] -= 2
            }
        }
    }

    private fun floodMatrix() {
        dfs(0, 0)
        for (i in 0 .. matrix.lastIndex) {
            for (j in 0 .. matrix[0].lastIndex) {
                if (matrix[i][j] < 1) matrix[i][j] += 1
            }
        }
    }

    private fun dfs(x: Int, y: Int) {
        if (matrix[x][y] != 0) return
        matrix[x][y] = -1
        try {
            dfs(x - 1, y)
        } catch (e: IndexOutOfBoundsException) {}
        try {
            dfs(x + 1, y)
        } catch (e: IndexOutOfBoundsException) {}
        try {
            dfs(x, y - 1)
        } catch (e: IndexOutOfBoundsException) {}
        try {
            dfs(x, y + 1)
        } catch (e: IndexOutOfBoundsException) {}
    }

    private fun checkValidRectangle(i: Int, j: Int): Boolean {
        for (x in min(points[i].x, points[j].x) .. max(points[i].x, points[j].x)) {
            for (y in min(points[i].y, points[j].y) .. max(points[i].y, points[j].y)) {
                if (matrix[x.toInt()][y.toInt()] == 0) return false
            }
        }
        return true
    }
}
