package aoc2015

import AocApplication
import utils.readInput

class Day03 : AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)[0]
		val res1 = this.part1(input)
		val res2 = this.part2(input)
		return res1 to res2
	}

	private fun part1(input: String): Long {
		val posSet = mutableSetOf(Pair(0L, 0L))
		var currPos = Pair(0L, 0L)
		input.forEach { it ->
			currPos = currPos.getNewPos(it)
			posSet.add(currPos)
		}
		return posSet.size.toLong()
	}

	private fun part2(input: String): Long {
		val posSet = mutableSetOf(Pair(0L, 0L))
		var santaPos = Pair(0L, 0L)
		var roboPos = Pair(0L, 0L)
		input.forEachIndexed { idx, c ->
			if (idx % 2 == 0) {
				santaPos = santaPos.getNewPos(c)
			} else {
				roboPos = roboPos.getNewPos(c)
			}
			posSet.add(santaPos)
			posSet.add(roboPos)
		}
		return posSet.size.toLong()
	}

	private fun Pair<Long, Long>.getNewPos(c: Char): Pair<Long, Long> =
		when (c) {
			'^' -> Pair(this.first - 1, this.second)
			'v' -> Pair(this.first + 1, this.second)
			'<' -> Pair(this.first, this.second - 1)
			else -> Pair(this.first, this.second + 1)
		}
}