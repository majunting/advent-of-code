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

    // matrix: [0] min X, [1] max X, [2] min Y, [3] max Y
    lateinit var matrix: MutableList<MutableList<Int>>
//    190243736 -> too low
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
//        matrix.forEach { it.println() }
//        findBoundary()
//        "-----------".println()
//        matrix.forEach { it.println() }
        var max = 0L
        for (i in points.indices) {
            for (j in i + 1..points.lastIndex) {
                if (checkLineCrossing(points[i], points[j]) && checkFourCorners(points[i], points[j])) {
                    val area = (abs(points[i].x - points[j].x) + 1) * (abs(points[i].y - points[j].y) + 1)
//                    points[i].println()
//                    points[j].println()
//                    area.println()
//                    "----------".println()
                    max = max(max, area)
                }

//                markColorFor2Points(i, j, false)
//                val isValid = checkValidRectangle(i, j)
//                if (isValid) {
//                    max = max(max, matrix.sumOf {
//                        it.count { n ->
//                            n == 3
//                        }
//                    }.toLong())
//                }
//                resetColor()
            }
        }
        return max
    }

    override fun preprocess(input: List<String>) {
        points = mutableListOf()
        input.forEach {
            points.add(
                Point(
                    (it.split(",")[0]).toLong(),
                    (it.split(",")[1]).toLong(),
                )
            )
        }
        val minX = points.minOf { it.x }
        val minY = points.minOf { it.y }
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }
//        val diffX: Int = (maxX - minX).toInt()
//        val diffY: Int  = (maxY - minY).toInt()
//        for (i in 0 .. points.lastIndex) {
//            points[i] = Point(points[i].x - minX + 1, points[i].y - minY + 1)
//        }
//        diffX.println()
//        diffY.println()
//        matrix = MutableList(diffX.toInt() + 2) { MutableList(diffY.toInt() + 2) { 0 } }
        matrix = mutableListOf()
        matrix.add(MutableList((maxX + 1).toInt()) { idx -> -1 })
        matrix.add(MutableList((maxX + 1).toInt()) { idx -> -1 })
        matrix.add(MutableList((maxY + 1).toInt()) { idx -> -1 })
        matrix.add(MutableList((maxY + 1).toInt()) { idx -> -1 })
    }

    private fun checkLineCrossing(p1: Point, p2: Point): Boolean {
        val minX = min(p1.x, p2.x)
        val minY = min(p1.y, p2.y)
        val maxX = max(p1.x, p2.x)
        val maxY = max(p1.y, p2.y)
        for (i in points.indices) {
            val j = if (i == points.lastIndex) 0 else i + 1
            if (points[i].x == points[j].x) {
                // horizontal line, crossing by y
                if (points[i].x in (minX + 1)..<maxX) {
                    val y1 = min(points[i].y, points[j].y)
                    val y2 = max(points[i].y, points[j].y)
                    if (y1 < minY && y2 > minY) return false
                    if (y1 < maxY && y2 > maxY) return false
                }
            } else {
                if (points[i].y in (minY + 1)..<maxY) {
                    val x1 = min(points[i].x, points[j].x)
                    val x2 = max(points[i].x, points[j].x)
                    if (x1 < minX && x2 > minX) return false
                    if (x1 < maxX && x2 > maxX) return false
                }
            }
        }
        val fp = points[0]
        val lp = points.last()
        if (fp.x == lp.x) {
            // horizontal line, crossing by y
            if (fp.x in (minX + 1)..<maxX) {
                val y1 = min(fp.y, lp.y)
                val y2 = max(fp.y, lp.y)
                if (y1 < minY && y2 > minY) return false
                if (y1 < maxY && y2 > maxY) return false
            }
        } else {
            if (fp.y in (minY + 1)..<maxY) {
                val x1 = min(fp.x, lp.x)
                val x2 = max(fp.x, lp.x)
                if (x1 < minY && x2 > minY) return false
                if (x1 < maxY && x2 > maxY) return false
            }
        }
        return true
    }

    private fun checkFourCorners(p1: Point, p2: Point): Boolean {
        val coords = listOf(
            min(p1.x, p2.x),
            max(p1.x, p2.x),
            min(p1.y, p2.y),
            max(p1.y, p2.y),
        )
        var pass = false
        points.forEach {
            if (it.x <= coords[0] && it.y <= coords[2]) {
                pass = true
            }
        }
        if (!pass) return false
        pass = false
        points.forEach {
            if (it.x >= coords[1] && it.y <= coords[2]) {
                pass = true
            }
        }
        if (!pass) return false
        pass = false
        points.forEach {
            if (it.x <= coords[0] && it.y >= coords[3]) {
                pass = true
            }
        }
        if (!pass) return false
        pass = false
        points.forEach {
            if (it.x >= coords[1] && it.y >= coords[3]) {
                pass = true
            }
        }
        return pass
    }

    private fun findBoundary() {
        points.forEach { pt ->
            if (matrix[0][pt.x.toInt()] == -1) {
                matrix[0][pt.x.toInt()] = pt.y.toInt()
            } else {
                if (pt.y < matrix[0][pt.x.toInt()]) matrix[0][pt.x.toInt()] = pt.y.toInt()
            }
            if (matrix[1][pt.x.toInt()] == -1) {
                matrix[1][pt.x.toInt()] = pt.y.toInt()
            } else {
                if (pt.y > matrix[1][pt.x.toInt()]) matrix[1][pt.x.toInt()] = pt.y.toInt()
            }
            if (matrix[2][pt.y.toInt()] == -1) {
                matrix[2][pt.y.toInt()] = pt.x.toInt()
            } else {
                if (pt.x < matrix[2][pt.y.toInt()]) matrix[2][pt.y.toInt()] = pt.x.toInt()
            }
            if (matrix[3][pt.y.toInt()] == -1) {
                matrix[3][pt.y.toInt()] = pt.x.toInt()
            } else {
                if (pt.x > matrix[3][pt.y.toInt()]) matrix[3][pt.y.toInt()] = pt.x.toInt()
            }
        }
    }

    private fun checkValidRectangleForTwoPoints(p1: Point, p2: Point): Long {
        var size = 0L
        val minX = min(p1.x, p2.x)
        val minY = min(p1.y, p2.y)
        val maxX = max(p1.x, p2.x)
        val maxY = max(p1.y, p2.y)
        for (i in minX..maxX) {
            if (minY >= matrix[0][i.toInt()] && maxY <= matrix[1][i.toInt()]) size += maxY - minY
            else return -1
        }
        for (i in minY..maxY) {
            if (minX >= matrix[2][i.toInt()] && maxX <= matrix[3][i.toInt()]) size += maxX - minX
            else return -1
        }
        return size
    }

    private fun markGreen() {
        // mark all points
        for (i in 0..<points.lastIndex) {
            markColorFor2Points(i, i + 1, true)
            "----".println()
        }
        markColorFor2Points(0, points.lastIndex, true)
        floodMatrix()
        matrix.forEach { it.println() }
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
        for (i in 0..matrix.lastIndex) {
            for (j in 0..matrix[0].lastIndex) {
                if (matrix[i][j] < 1) matrix[i][j] += 1
            }
        }
    }

    private fun dfs(x: Int, y: Int) {
        if (matrix[x][y] != 0) return
        matrix[x][y] = -1
        try {
            dfs(x - 1, y)
        } catch (e: IndexOutOfBoundsException) {
        }
        try {
            dfs(x + 1, y)
        } catch (e: IndexOutOfBoundsException) {
        }
        try {
            dfs(x, y - 1)
        } catch (e: IndexOutOfBoundsException) {
        }
        try {
            dfs(x, y + 1)
        } catch (e: IndexOutOfBoundsException) {
        }
    }

    private fun checkValidRectangle(i: Int, j: Int): Boolean {
        for (x in min(points[i].x, points[j].x)..max(points[i].x, points[j].x)) {
            for (y in min(points[i].y, points[j].y)..max(points[i].y, points[j].y)) {
                if (matrix[x.toInt()][y.toInt()] == 0) return false
            }
        }
        return true
    }
}
