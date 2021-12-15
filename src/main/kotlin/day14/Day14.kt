package day14

import Input
import readInput

typealias Pattern = Pair<Char, Char>

var rules = mapOf<String, Char>()

fun main() {

    fun solve(t: Map<String, Long>, lastChar: Char, steps: Int): Long {
        
        return (0 until steps)
            .fold(t) { polymer, _ ->
                polymer.buildPolymer()
            }.map {
                it.key.first() to it.value
            }.groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }
            .values
            .sortedDescending().let {
                it.first() - it.last()
            }
    }

    fun part1(input: Input): Long {
        rules = input.parseRules()

        return solve(input.first().toTemplate(), input.first().last(), 10)

    }

    fun part2(input: Input): Long {
        rules = input.parseRules()

        return solve(input.first().toTemplate(), input.first().last(), 10)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day14/test_input")
    val input = readInput("day14/input")

    val part1 = part1(testInput)
    println(part1)
    check(part1 == 1588L)
    println(part1(input))

    check(part2(testInput) == 2188189693529L)
    println(part2(input))
}

fun String.toTemplate() = windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

fun Map<String, Long>.buildPolymer(): Map<String, Long> {
    val result = mutableMapOf<String, Long>()
    this.forEach { (pair, count) ->
        val insertion = rules[pair]
        result.incrementAt("${pair.first()}$insertion", count)
        result.incrementAt("$insertion${pair.last()}", count)
    }
    return result
}

fun MutableMap<String, Long>.incrementAt(key: String, count: Long) {
    this[key] = (this[key]?: 0) + count
}


fun Input.parseRules(): Map<String, Char> =
    drop(2).associate {
        it.substring(0..1) to it.last()
    }