package template

import Input
import readInput

fun main() {
    
    fun part1(input: Input): Int {
        return 0
    }

    fun part2(input: Input): Int {
        return 0
            
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("template/test_input")
    val input = readInput("template/input")

    check(part1(testInput) == 0)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}
