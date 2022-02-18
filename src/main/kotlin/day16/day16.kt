package day16

import Input
import readInput

fun main() {

    fun part1(input: Input): Int {

        val bits = input.toBits()
        
        val version = bits.substring(0,2).toInt(2)
        val type = bits.substring(3,5).toInt(2)
        
        
        return bits.toInt()
    }

    fun part2(input: Input): Int {
        return 0

    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day16/test_input")
    val input = readInput("day16/input")

    check(part1(testInput) == 0)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}

val hexMap = mapOf(
    '0' to "0000", 
    '1' to "0001", 
    '2' to "0010", 
    '3' to "0011", 
    '4' to "0100", 
    '5' to "0101", 
    '6' to "0110", 
    '7' to "0111", 
    '8' to "1000", 
    '9' to "1001", 
    'A' to "1010", 
    'B' to "1011", 
    'C' to "1100", 
    'D' to "1101", 
    'E' to "1110", 
    'F' to "1111", 
)

fun Input.toBits(): String =
    first().fold("") { acc, c ->
        acc + hexMap[c]
    }
