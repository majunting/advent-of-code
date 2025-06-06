import aoc2024.chooseClassFromDay2024

interface AocApplication {
    fun run(fileName: String): Pair<Any, Any>
}

fun main(args: Array<String>) {
    val day = 21

    runApp2024(day, true)
    runApp2024(day, false)
}

fun runApp2024(day: Int, isTest: Boolean = false) {
    val app: AocApplication = chooseClassFromDay2024(day)
    when (isTest) {
        true -> app.run("day${day.toString().padStart(2, '0')}test").printRes(day, true)
        false -> app.run("day${day.toString().padStart(2, '0')}").printRes(day, false)

    }
}


fun Pair<Any, Any>.printRes(day: Int, isTest: Boolean = false) {
    val dayStr =
        if (isTest) "day${day.toString().padStart(2, '0')}-test" else "day-${day.toString().padStart(2, '0')}"
    println("${dayStr}-1: $first")
    println("${dayStr}-2: $second")
}