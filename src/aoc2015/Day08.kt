package aoc2015

import AocApplication
import utils.isNumeric
import utils.println
import utils.readInput

class Day08 : AocApplication {
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		val res1 = part1(input)
		val res2 = part2(input)
		return res1 to res2
	}

	private fun part1(input: List<String>): Int {
		val charCount = input.fold(0) { acc, it ->
			acc + it.length
		}
		val stringLength = input.fold(0) { acc, it ->
			var escape = false
			var hex = 0
			var length = 0
			for (i in 1 ..< it.length - 1) {
				if (hex > 0) {
					hex -= 1
					continue
				}
				if (escape) {
					escape = false
					length += 1
					if (it[i] == 'x') hex = 2
				} else {
					if (it[i] == '\\') escape = true
					else length += 1
				}
			}
			acc + length
		}
		charCount.println()
		stringLength.println()
		return charCount - stringLength
	}

	private fun part2(input: List<String>): Int {
		val encodedLength = input.fold(0) { acc, it ->
			var extra = 4
			for (i in 1 ..< it.length - 1) {
				if (it[i] == '\"' || it[i] == '\\') extra += 1
			}
			acc + extra
		}
		encodedLength.println()
		return encodedLength
	}
}