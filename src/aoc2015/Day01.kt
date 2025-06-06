package aoc2015

import AocApplication
import utils.readInput

class Day01: AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)[0]
		val res1 = this.part1(input)
		val res2 = this.part2(input)
		return res1 to res2
	}

	private fun part1(input: String): Long {
		var count = 0L
		for (i in input) {
			if (i == '(') count++
			else count--
		}
		return count
	}

	private fun part2(input: String): Long {
		var count = 0L
		for (i in input.indices) {
			if (input[i] == '(') count++
			else count--
			if (count == -1L) return (i + 1).toLong()
		}
		return 0
	}
}