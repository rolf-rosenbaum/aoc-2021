package `2016`

import readInput


fun main() {
    fun part1(input: List<String>): Int = input.count { line ->
        line.trim().split("\\s+".toRegex())
            .map(String::toInt).sorted()
            .let {
                it.last() < it.first() + it[1]
            }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            line.trim().split("\\s+".toRegex())
                .map(String::toInt)
        }.map {
            listOf(it.first(), it[1], it.last())
        }.let { lists -> lists.map { it.first() } + lists.map { it[1] } + lists.map { it.last() } }
            .chunked(size = 3) { lengths ->
                lengths.sortedDescending().let {
                    it[0] < it[1] + it[2]
                }
            }.count { it }

    }

    val testInput = readInput("2016/day03_test_input")
//    check(part1(testInput) == 0)
    check(part2(testInput) == 8)

    val input = readInput("2016/day03_input")
    println(part1(input))
    println(part2(input))
}

