package aoc2025

import AocApplication
import utils.Point3D
import utils.distance3D
import utils.println
import java.math.BigDecimal
import kotlin.math.min

class Day08 : AocApplication {
    var points = mutableListOf<Pair<Point3D, Int>>()
    lateinit var distanceList: MutableList<Triple<Double, Int, Int>>
    var connections = 0


    override fun defaultPart1(input: List<String>): Any {
        preprocess(input)
        distanceList = distanceList.subList(0, connections)
        distanceList.println()
        distanceList.forEach {
            updateByDistancePair(it)
        }
        val counts = getCounts()
        return counts[0] * counts[1] * counts[2]
    }

    override fun defaultPart2(input: List<String>): Any {
        preprocess(input)
        var idx = -1
        var counts: MutableList<Long>
        do {
            idx++
            updateByDistancePair(distanceList[idx])
            counts = getCounts()
        } while (idx < distanceList.size && counts[0] != points.size.toLong())
        idx.println()
        distanceList[idx].println()
        points[distanceList[idx].second].first.println()
        points[distanceList[idx].third].first.println()
        return BigDecimal(points[distanceList[idx].second].first.x * points[distanceList[idx].third].first.x).toPlainString()
    }
    
    private fun preprocess(input: List<String>) {
        points.clear()
        input.forEachIndexed { index, it ->
            points.add(
                Pair(
                    Point3D(
                        it.split(",")[0].toDouble(),
                        it.split(",")[1].toDouble(),
                        it.split(",")[2].toDouble(),
                    ),
                    index
                )
            )
        }
        connections = if(points.size == 20) 10 else 1000
        distanceList = mutableListOf()
        for (i in 0 ..points.lastIndex) {
            for (j in i + 1 .. points.lastIndex) {
                distanceList.add(
                    Triple(
                        distance3D(points[i].first, points[j].first),
                        i,
                        j
                    )
                )
            }
        }
        distanceList = distanceList.sortedBy { it.first }.toMutableList()
    }

    private fun findAllAffected(checked: List<Int>, toCheck: List<Int>): List<Int> {
        val allChecked = checked.plus(toCheck).distinct()
        var newList = mutableListOf<Int>()
        points.forEachIndexed { index, it ->
            if (it.second in toCheck || index in toCheck) {
                newList.add(it.second)
                newList.add(index)
            }
        }
        newList = newList.filter { it !in checked } as MutableList<Int>
        if (newList.isEmpty()) return allChecked
        return findAllAffected(allChecked, newList)
    }

    private fun updateByDistancePair(dist: Triple<Double, Int, Int>) {
        val p1 = dist.second
        val p2 = dist.third
        val toUpdate =
            findAllAffected(listOf(), listOf(p1, p2, points[p1].second, points[p2].second))
        val min = toUpdate.min()
        toUpdate.forEach {
            points[it] = Pair(points[it].first, min)
        }
    }

    private fun getCounts(): MutableList<Long> {
        val counts = MutableList(points.size) { 0L }
        points.forEach {
            counts[it.second]++
        }
        counts.sortDescending()
        return counts
    }
}