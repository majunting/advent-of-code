package aoc2015

import AocApplication
import utils.md5
import utils.readInput

class Day04 : AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)[0]
		val res1 = this.part1(input)
		val res2 = this.part2(input)
		return res1 to res2
	}

	private fun part1(input: String): Long {
		for (i in 1..1000000) {
			if ((input + i.toString()).md5().startsWith("00000")) return i.toLong()
		}
		return 0
	}

	private fun part2(input: String): Long {
		for (i in 1..100000000) {
			if ((input + i.toString()).md5().startsWith("000000")) return i.toLong()
		}
		return 0
	}
}