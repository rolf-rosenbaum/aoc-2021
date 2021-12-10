package day10

import Input
import readInput

fun main() {

    fun part1(input: Input): Int {
        return input
            .sumOf { valueMap[it.findFirstIllegalCharacter()] ?: 0 }
    }

    fun String.score() = fold(0L) { acc, c ->
        acc * 5 + valueMapPart2[c]!!.toLong()
    }

    fun part2(input: Input): Long {

        val scores = input.filter { it.findFirstIllegalCharacter() == null }
            .map {
                it.findClosingSequence()
            }.map(String::score).sorted()

        return scores[(scores.size - 1) / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10/test_input")
    val input = readInput("day10/input")

    check(part1(testInput) == 26397)
    println(part1(input))

    check(part2(testInput) == 288957L)
    println(part2(input))
}

fun String.findClosingSequence(): String {
    val parsed = mutableListOf<Char>()
    forEach {
        if (it in opening) {
            parsed.add(it)
        } else {
            parsed.removeLast()
        }
    }
    return parsed.reversed().map {
        it.correspondingCloser()
    }.joinToString("")
}

fun String.findFirstIllegalCharacter(): Char? {
    val parsed = mutableListOf<Char>()
    forEach {
        if (it in opening) {
            parsed.add(it)
        } else {
            if (parsed.last() == it.correspondingOpener()) {
                parsed.removeLast()
            } else {
                return it
            }
        }
    }
    return null
}

fun Char.correspondingOpener() = opening[closing.indexOf(this)]
fun Char.correspondingCloser() = closing[opening.indexOf(this)]

val valueMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val valueMapPart2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

val opening = listOf('[', '<', '{', '(')
val closing = listOf(']', '>', '}', ')')