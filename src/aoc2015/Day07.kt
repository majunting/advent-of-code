package aoc2015

import AocApplication
import utils.isNumeric
import utils.println
import utils.readInput

data class Instruction(
	val out: String,
	val operator: String,
	val param1: String? = null,
	val param2: String? = null,
	val res: Int? = null
)

class Day07 : AocApplication {
	var operations = mutableListOf<Instruction>()
	val solved = mutableMapOf<String, Int>()
	override fun run(fileName: String): Pair<Any, Any> {
		val input = readInput(fileName)
		operations.clear()
		solved.clear()
		operations = input.map { it ->
			parseInstruction(it)
		} as MutableList<Instruction>
		val part1 = part1("a")
		solved.clear()
		input.forEach { parseInstruction(it) }
		solved["b"] = part1
		return part1 to part1("a")
	}

	private fun part1(query: String): Int {
		while (!solved.contains(query)) {
			operations.forEach { it ->
				when (it.operator) {
					"ASSIGN" -> if (it.param1 != null && solved.contains(it.param1)) {
						solved.put(it.out, solved[it.param1]!!)
					}
					"NOT" -> {
						val param = if (solved.contains(it.param1)) solved[it.param1]!! else if (it.param1!!.isNumeric()) it.param1.toInt() else null
						if (param != null) {
							solved.put(it.out, 65535 - param!!)
						}
					}
					"AND" -> {
						val param1 = if (solved.contains(it.param1)) solved[it.param1]!! else if (it.param1!!.isNumeric()) it.param1.toInt() else null
						val param2 = if (solved.contains(it.param2)) solved[it.param2]!! else if (it.param2!!.isNumeric()) it.param2.toInt() else null
						if (param1 != null && param2 != null) {
							solved.put(it.out, param1.and(param2))
						}
					}
					"OR" -> {
						val param1 = if (solved.contains(it.param1)) solved[it.param1]!! else if (it.param1!!.isNumeric()) it.param1.toInt() else null
						val param2 = if (solved.contains(it.param2)) solved[it.param2]!! else if (it.param2!!.isNumeric()) it.param2.toInt() else null
						if (param1 != null && param2 != null) {
							solved.put(it.out, param1.or(param2))
						}
					}
					"LSHIFT" -> {
						val param1 = if (solved.contains(it.param1)) solved[it.param1]!! else if (it.param1!!.isNumeric()) it.param1.toInt() else null
						val param2 = if (solved.contains(it.param2)) solved[it.param2]!! else if (it.param2!!.isNumeric()) it.param2.toInt() else null
						if (param1 != null && param2 != null) {
							solved.put(it.out, param1.shl(param2))
						}
					}
					"RSHIFT" -> {
						val param1 = if (solved.contains(it.param1)) solved[it.param1]!! else if (it.param1!!.isNumeric()) it.param1.toInt() else null
						val param2 = if (solved.contains(it.param2)) solved[it.param2]!! else if (it.param2!!.isNumeric()) it.param2.toInt() else null
						if (param1 != null && param2 != null) {
							solved.put(it.out, param1.shr(param2))
						}
					}
				}
			}
		}
		return solved[query]!!
	}

	private fun parseInstruction(inst: String): Instruction {
		val output = inst.split(" -> ")[1]
		val input = inst.split(" -> ")[0]
		if (input.split(" ").size == 1) {
			if (input.isNumeric()) {
				solved.put(output, input.toInt())
				return Instruction(out = output, operator = "ASSIGN", res = input.toInt())
			} else {
				return Instruction(out = output, operator = "ASSIGN", param1 = input)
			}
		} else {
			return if (input.split(" ").size == 2) {
				Instruction(out = output, operator = "NOT", param1 = input.split(" ")[1])
			} else {
				Instruction(
					out = output,
					operator = input.split(" ")[1],
					param1 = input.split(" ")[0],
					param2 = input.split(" ")[2]
				)
			}
		}
	}
}