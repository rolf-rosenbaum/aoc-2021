package day06

import Input
import readInput

fun main() {
    fun part1(input: Input): Long {
        return input.first().split(",").map(String::toInt)
            .groupBy { it }
            .mapValues { it.value.count().toLong() }
            .toMutableMap()
            .countAfterDays(80)
    }

    fun part2(input: Input): Long {
        return input.first().split(",").map(String::toInt)
            .groupBy { it }
            .mapValues { it.value.count().toLong() }
            .toMutableMap()
            .countAfterDays(256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day06/test_input")
    val input = readInput("day06/input")

    val part1 = part1(testInput)
    println(part1)
    check(part1 == 5934L)
    println(part1(input))

    check(part2(testInput) == 26984457539L)
    println(part2(input))
}

fun MutableMap<Int, Long>.countAfterDays(days: Int): Long {
    val swarm = this
    repeat(days) {
        val foo = swarm[0] ?: 0
        (0..5).map { n ->
            swarm[n] = swarm[n+1] ?: 0
        }
        swarm[6] = (swarm[7] ?: 0) + foo
        swarm[7] = swarm[8] ?: 0
        swarm[8] = foo
    }
    return swarm.values.sum()
}
