package aoc2015

import AocApplication
import utils.isNumeric
import utils.println
import utils.readInput

class Day09 : AocApplication {
	val distanceMap = mutableMapOf<Pair<String, String>, Int>()
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		distanceMap.clear()
		parseDistanceMap(input)
		distanceMap.println()
		val res1 = part1(input)
		val res2 = part2(input)
		return 1 to 1
	}

	private fun part1(input: List<String>): Long {
		return 1L
	}

	private fun part2(input: List<String>): Long {
		return 1L
	}

	private fun lexPair(str1: String, str2: String): Pair<String, String> {
		return if (str1.compareTo(str2) < 0) Pair(str1, str2) else Pair(str2, str1)
	}

	private fun parseDistanceMap(input: List<String>) {
		input.forEach { it ->
			distanceMap[lexPair(it.split(" to ")[0], it.split(" = ")[0].split(" to ")[1])] =
				it.split(" = ")[1].toInt()
		}
	}
}