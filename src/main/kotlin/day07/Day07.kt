package day07

import Input
import csvToInts
import readInput
import kotlin.math.abs

fun main() {

    fun List<Int>.fuelConsumptionPart1(candidate: Int) =
        sumOf { abs(it - candidate) }

    fun part1(input: Input): Int {

        val positions = input.first().csvToInts()
        val optimalPosition =  (0..positions.maxOf { it }).minByOrNull { candidate ->
            positions.fuelConsumptionPart1(candidate)
        }!!
        return positions.fuelConsumptionPart1(optimalPosition)
    }


    fun List<Int>.fuelConsumptionPart2(candidate: Int) = sumOf {
        val steps = abs(it - candidate)
        (steps * (steps + 1)) / 2
    }

    fun part2(input: Input): Int {
        val positions = input.first().csvToInts()
        val optimalPosition =  (0..positions.maxOf { it }).minByOrNull { candidate ->
            positions.fuelConsumptionPart2(candidate)
        }!!
        return positions.fuelConsumptionPart2(optimalPosition)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07/test_input")
    val input = readInput("day07/input")

    check(part1(testInput) == 37)
    println(part1(input))

    check(part2(testInput) == 168)
    println(part2(input))
}
