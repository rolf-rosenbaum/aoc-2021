package template

import Input
import readInput
import toInts

fun main() {

    fun Input.sumUp(windowSize: Int) = toInts()
        .windowed(windowSize)
        .zipWithNext()
        .count {
            it.first.sum() < it.second.sum()
        }

    fun part1(input: Input): Int {
        return input.sumUp(1)
    }

    fun part2(input: Input): Int {
        return input.sumUp(3)
            
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/test_input")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01/input")
    println(part1(input))
    println(part2(input))
}
