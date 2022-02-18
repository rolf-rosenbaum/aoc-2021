package `2016`

import readInput
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {

        var degrees = 0
        var x = 0
        var y = 0
        input.first().split(", ").forEach { movement ->
            degrees += if (movement.substring(0, 1) == "R") 1 else -1
            val steps = movement.substring(1).toInt()
            when (degrees % 4) {
                0 -> y += steps
                1, -3 -> x += steps
                2, -2 -> y -= steps
                3, -1 -> x -= steps
            }
        }
        return abs(x) + abs(y)
    }

    fun part2(input: List<String>): Int {
        var degrees = 0
        var x = 0
        var y = 0
        val visited = mutableListOf<Pair<Int, Int>>()
        visited.add(0 to 0)
        input.first().split(", ").forEach { movement ->
            degrees += if (movement.substring(0, 1) == "R") 1 else -1
            val steps = movement.substring(1).toInt()
            when (degrees % 4) {
                0 -> repeat(steps) {
                    y++
                    if (visited.contains(x to y)) return abs(x) + abs(y) 
                    visited.add (x to y)
                }
                1, -3 -> repeat(steps) {
                    x++
                    if (visited.contains(x to y)) return abs(x) + abs(y)
                    visited.add (x to y)
                }
                2, -2 ->  repeat(steps) {
                    y--
                    if (visited.contains(x to y)) return abs(x) + abs(y)
                    visited.add (x to y)
                }
                3, -1 -> repeat(steps) {
                    x--
                    if (visited.contains(x to y)) return abs(x) + abs(y)
                    visited.add (x to y)
                }
            }
        }
        error("this shouldn't happen")
    }

    val testInput = readInput("2016/day01_test_input")
    check(part1(testInput) == 12)
//    check(part2(testInput) == 0)

    val input = readInput("2016/day01_input")
    println(part1(input))
    println(part2(input))
}
