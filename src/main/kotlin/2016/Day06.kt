package `2016`

import readInput


fun main() {
    
    fun part1(input: List<String>): String {
        return (0 until input.first().length).map { index ->
            val col = input.map { it[index] }
            col.maxByOrNull { c -> col.count { c == it } }!!
        }.joinToString("")
    }


    fun part2(input: List<String>): String {
        return (0 until input.first().length).map { index ->
            val col = input.map { it[index] }
            col.minByOrNull { c -> col.count { c == it } }!!
        }.joinToString("")
    }

    
    val testInput = readInput("2016/day06_test_input")
    check(part1(testInput) == "easter")
    check(part2(testInput) == "advent")

    val input = readInput("2016/day06_input")
    println(part1(input))
    println(part2(input))
}
