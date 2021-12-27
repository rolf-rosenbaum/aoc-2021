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
fun String.toSFN(): SFN {
    var sfn: SFN? = null
    forEachIndexed { index, c ->
        if (c == '[') {
            sfn = SFN()
        } else if (c.isDigit()) {
            if (this[index + 1] == ',') {
                sfn = sfn!!.copy(leftVal = c.digitToInt())
            } else if (this[index + 1] == '[') {
                sfn = sfn!!.copy(right = SFN(), left = substring(index+1).toSFN())
            } else {
                sfn = sfn!!.copy(rightVal = c.digitToInt())
            }
        } 

    }
    return sfn!!

    error("we should not end up here")
}