package aoc2015

import AocApplication
import utils.readInput

class Day05 : AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		val res1 = this.part1(input)
		val res2 = this.part2(input)
		return res1 to res2
	}

	private fun part1(input: List<String>): Long =
		input.count { it.isNiceString() }.toLong()

	private fun part2(input: List<String>): Long =
		input.count { it.isPart2Nice() }.toLong()

	private fun String.isNiceString(): Boolean {
		if (this.contains("ab") || this.contains("cd") || this.contains("pq") || this.contains("xy")) return false
		var vowelCount = if (this[0].isVowel()) 1 else 0
		var containsDup = false
		for (i in 1 ..< this.length) {
			if (this[i] == this[i - 1]) containsDup = true
			vowelCount += if (this[i].isVowel()) 1 else 0
		}
		return vowelCount > 2 && containsDup
	}

	private fun Char.isVowel(): Boolean = this in listOf('a', 'e', 'i', 'o', 'u')

	private fun String.isPart2Nice(): Boolean {
		var cond1 = false
		var cond2 = false
		for (i in 0 ..< this.length - 2) {
			if (this.substring(i + 2).contains(this.substring(i, i + 2))) cond1 = true
		}
		for (i in 1 ..< this.length - 1) {
			if (this[i - 1] == this[i + 1]) cond2 = true
		}
		return cond1 && cond2
	}
}