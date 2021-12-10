package day03

import Input
import readInput

fun main() {
    fun part1(input: Input): Int {

        val cols: MutableMap<Int, MutableList<Char>> = input.toColumnMap()

        val gammaRate = cols.find(::mostCommon).joinToString("").toInt(2)
        val epsilonRate = cols.find(::leastCommon).joinToString("").toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: Input): Int {
        return oxygenGeneratorRate(input, 0).toInt(2) * co2ScrubberRate(input, 0).toInt(2)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun Input.toColumnMap(): MutableMap<Int, MutableList<Char>> {
    val cols: MutableMap<Int, MutableList<Char>> = mutableMapOf()
    forEach { line ->
        line.mapIndexed { index, c: Char ->
            val col = cols[index] ?: mutableListOf()
            col.add(c)
            cols[index] = col
        }
    }
    return cols
}

fun oxygenGeneratorRate(input: Input, index: Int): String {
    if (input.size == 1)
        return input.first()

    val mostCommon = input.toColumnMap().find(::mostCommon)[index]

    return oxygenGeneratorRate(
        input = input.filter { line -> line[index] == mostCommon },
        index = index + 1
    )
}

fun co2ScrubberRate(input: Input, index: Int): String {
    if (input.size == 1)
        return input.first()
    val leastCommon = input.toColumnMap().find(::leastCommon)[index]

    return co2ScrubberRate(
        input = input.filter { line ->
            line[index] == leastCommon
        }, index = index + 1
    )
}

fun MutableMap<Int, MutableList<Char>>.find(f: (Iterable<Char>) -> Char): List<Char> =
    map { (_, col) ->
        f(col)
    }

fun mostCommon(col: Iterable<Char>) =
    if (col.count { it == ZERO } > col.count { it == ONE }) {
        '0'
    } else {
        '1'
    }

fun leastCommon(col: Iterable<Char>) =
    if (col.count { it == ZERO } <= col.count { it == ONE }) {
        '0'
    } else {
        '1'
    }

const val ZERO = '0'
const val ONE = '1'