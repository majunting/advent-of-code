package aoc2015

import AocApplication
import utils.println
import utils.readInput

class Day06 : AocApplication {
	var lightMap = MutableList(1000) { MutableList(1000) { 0 } }
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		val instructions = input.map { it ->
			val type = if (it.startsWith("turn on")) 1 else if (it.startsWith("turn off")) 2 else 3
			Pair(
				type, Pair(
					Pair(
						it.split(",")[0].split(" ")[if (type == 3) 1 else 2].toInt(),
						it.split(",")[1].split(" ")[0].toInt()
					), Pair(
						it.split(",")[1].split(" ")[2].toInt(),
						it.split(",")[2].toInt()
					)
				)
			)
		}
		instructions.println()
		val res1 = this.part1(instructions)
		lightMap = MutableList(1000) { MutableList(1000) { 0 } }
		countLights().println()
		val res2 = this.part2(instructions)
		return res1 to res2
	}

	private fun part1(instructions: List<Pair<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>>>): Int {
		instructions.forEach { it ->
			for (i in it.second.first.first..it.second.second.first) {
				for (j in it.second.first.second..it.second.second.second) {
					when (it.first) {
						1 -> lightMap[i][j] = 1
						2 -> lightMap[i][j] = 0
						else -> lightMap[i][j] = lightMap[i][j].xor(1)
					}
				}
			}
		}
		return countLights()
	}

	private fun part2(instructions: List<Pair<Int, Pair<Pair<Int, Int>, Pair<Int, Int>>>>): Int {
		instructions.forEach { it ->
			for (i in it.second.first.first..it.second.second.first) {
				for (j in it.second.first.second..it.second.second.second) {
					when (it.first) {
						1 -> lightMap[i][j] += 1
						2 -> lightMap[i][j] = if (lightMap[i][j] == 0) 0 else lightMap[i][j] - 1
						else -> lightMap[i][j] += 2
					}
				}
			}
		}
		return countLights()
	}

	private fun countLights(): Int = lightMap.sumOf { it -> it.sum() }

}