package day08

import Input
import readInput
import kotlin.math.pow

fun main() {

    fun part1(input: Input): Int {
        return input.sumOf { line ->
            line.split(" | ").last().split(" ")
                .count { it.length in listOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: Input): Int {

        val digitMap = input.map { line ->
            line.split(" | ").first().split(" ").map { it1 ->
                it1.toCharArray().sorted().joinToString("")
            }.determineDigits()
        }
        
        return input.mapIndexed { i, line ->
            line.split(" | ").last().split(" ").map {
                it.toCharArray().sorted().joinToString("")
            }.reversed()
                .mapIndexed { index, digit ->
                    digitMap[i][digit]!! * 10.0.pow(index).toInt()
                }
        }.flatten().sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day08/test_input")
    val input = readInput("day08/input")

    val part1 = part1(testInput)
    println(part1)
    check(part1 == 26)
    println(part1(input))

    check(part2(testInput) == 61229)
    println(part2(input))
}


fun List<String>.determineDigits(): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val seven = first { it.length == 3 }.toCharArray().sorted().joinToString("")
    seven.let { result[it] = 7 }
    val one = first { it.length == 2 }
    one.toCharArray().sorted().joinToString("").let { result[it] = 1 }
    val four = first { it.length == 4 }.toCharArray().sorted().joinToString("")
    four.let { result[it] = 4 }
    val eight = first { it.length == 7 }
    eight.toCharArray().sorted().joinToString("").let { result[it] = 8 }
    val three = filter { it.length == 5 }.first { it.toCharArray().sorted().containsAll(seven.toCharArray().sorted()) }
    three.let { result[it] = 3 }
    val nine = filter { it.length == 6 }.first { it.toCharArray().sorted().containsAll(three.toCharArray().sorted()) }
    nine.let { result[it] = 9 }
    val two = filter { it.length == 5 }.first { !nine.toCharArray().sorted().containsAll(it.toCharArray().sorted()) }
    two.let { result[it] = 2 }
    val five = filter { it.length == 5 }.first {
        it.toCharArray().sorted() !in listOf(
            two.toCharArray().sorted(),
            three.toCharArray().sorted()
        )
    }
    five.let { result[it] = 5 }
    val six = filter { it.length == 6 }.first {
        it.toCharArray().sorted().containsAll(five.toCharArray().sorted()) && it != nine
    }
    six.let { result[it] = 6 }
    val zero = filter { it.length == 6 }.first {
        it.toCharArray().sorted() !in listOf(
            six.toCharArray().sorted(),
            nine.toCharArray().sorted()
        )
    }
    zero.let { result[it] = 0 }
    return result
}
