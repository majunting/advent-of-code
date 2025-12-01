import utils.chooseClass
import utils.readInput

interface AocApplication {
    fun run(year: Int, fileName: String): Pair<Any, Any> {
        val input = readInput(year, fileName)
        return defaultPart1(input) to defaultPart2(input)
    }

    fun defaultPart1(input: List<String>): Any = 0

    fun defaultPart2(input: List<String>): Any = 0
}

fun main(args: Array<String>) {
    val day = 1
    val year = 2025

    runApp(year, day, true)
    runApp(year, day, false)
}

fun runApp(year: Int, day: Int, isTest: Boolean = false) {
    val app: AocApplication = chooseClass(year, day)
    when (isTest) {
        true -> app.run(year, "day${day.toString().padStart(2, '0')}test").printRes(day, true)
        false -> app.run(year, "day${day.toString().padStart(2, '0')}").printRes(day, false)
    }
}


fun Pair<Any, Any>.printRes(day: Int, isTest: Boolean = false) {
    val dayStr =
        if (isTest) "day${day.toString().padStart(2, '0')}-test" else "day-${day.toString().padStart(2, '0')}"
    println("${dayStr}-1: $first")
    println("${dayStr}-2: $second")
}