package day18

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
    val testInput = readInput("day18/test_input")
    val input = readInput("day18/input")

    check(part1(testInput) == 0)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}


data class SFN(
    val leftVal: Int? = null,
    val left: SFN? = null,
    val rightVal: Int? = null,
    val right: SFN? = null
) {

    operator fun plus(other: SFN?) = SFN(left = this, right = other)
    override fun toString(): String {
        val l = leftVal ?: left
        val r = rightVal ?: right

        return "[$l,$r]"
    }
}

// [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
fun String.toSFN(num: SFN? = null): SFN {
    
    println(this)
    if (1 == length) {
        println(num)
        return num!!
    } 
    val c = this.first()
    if (c == '[') {
        val number = SFN()
        return number.copy(left = substring(1).toSFN(number))
    }
    if (c == ']') {
        return num!!
    }
    if (c == ',') {
        return num!!.copy(right = substring(1).toSFN(num))
    }
    if (c.isDigit()) {
        if(this[1] == ',') {
            return substring(1).toSFN(num!!.copy(leftVal = c.digitToInt()))
        } else 
        if(this[1] == ']') {
            return substring(1).toSFN(num!!.copy(rightVal = c.digitToInt()))
        }
    }
    
    error("we should not end up here")
}