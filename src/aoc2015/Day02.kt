package aoc2015

import AocApplication
import java.lang.Long.min
import kotlin.math.max
import utils.println
import utils.readInput

class Day02 : AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		val dimensions = input.map { it ->
			Triple(
				it.split("x")[0].toLong(),
				it.split('x')[1].toLong(),
				it.split('x')[2].toLong()
			)
		}
		dimensions.println()
		val res1 = this.part1(dimensions)
		val res2 = this.part2(dimensions)
		return res1 to res2
	}

	private fun part1(dimensions: List<Triple<Long, Long, Long>>): Long =
		dimensions.fold(0L) { acc, it ->
			val face1 = it.first * it.second
			val face2 = it.first * it.third
			val face3 = it.second * it.third
			acc + 2 * face1 + 2 * face2 + 2 * face3 + min(face1, min(face2, face3))
		}

	private fun part2(dimensions: List<Triple<Long, Long, Long>>): Long =
		dimensions.fold(0L) { acc, it ->
			acc + 2 * (it.first + it.second + it.third) - 2 * max(
				it.first,
				max(it.second, it.third)
			) + (it.first * it.second * it.third)
		}

}