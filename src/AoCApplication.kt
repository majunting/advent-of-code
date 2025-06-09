import utils.loadAocApplication

interface AocApplication {
    fun run(fileName: String): Pair<Any, Any>
}

fun main(args: Array<String>) {
    val year = 2015
    val day = 9
    val app: AocApplication = loadAocApplication(year, day)
    app.runApp(year, day, true)
    app.runApp(year, day, false)
}

fun AocApplication.runApp(year: Int, day: Int, isTest: Boolean = false) {
    when (isTest) {
        true -> this.run("src/aoc$year/resources/day${day.toString().padStart(2, '0')}test.txt").printRes(day, true)
        false -> this.run("src/aoc$year/resources/day${day.toString().padStart(2, '0')}.txt").printRes(day, false)

    }
}

fun Pair<Any, Any>.printRes(day: Int, isTest: Boolean = false) {
    val dayStr =
        if (isTest) "day${day.toString().padStart(2, '0')}-test" else "day-${day.toString().padStart(2, '0')}"
    println("${dayStr}-1: $first")
    println("${dayStr}-2: $second")
}