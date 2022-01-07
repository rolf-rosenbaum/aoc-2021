package day18

import Input
import readInput

typealias SFN = String

val numberRegEx = """\d+""".toRegex()

fun main() {
    fun part1(input: Input): Int {
        return input.reduce(SFN::add).magnitude
    }

    fun part2(input: Input): Int {

        return input.flatMapIndexed { index, sfn1 ->
            input.drop(index).flatMap { sfn2 ->
                listOf(sfn1.add(sfn2).magnitude, sfn2.add(sfn1).magnitude)
            }
        }.maxOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day18/test_input")
    val input = readInput("day18/input")

    check(part1(testInput) == 4140)
    println(part1(input))

    check(part2(testInput) == 3993)
    println(part2(input))
}

fun SFN.addToLeftVal(value: Int): SFN {
    val match = numberRegEx.findAll(this).firstOrNull()
    return if (match == null) this else {
        replaceRange(match.range, (match.value.toInt() + value).toString())
    }
}

fun SFN.addToRightVal(value: Int): SFN {
    val match = numberRegEx.findAll(this).lastOrNull()
    return if (match == null) this else {
        replaceRange(match.range, (match.value.toInt() + value).toString())
    }
}

fun SFN.add(sfn: SFN): SFN = "[$this,$sfn]".reduce()

fun SFN.explode(): Pair<SFN, Boolean> {
    val stack = mutableListOf<Int>()
    this.forEachIndexed { index, char ->
        when (char) {
            '[' -> stack.add(index)
            ']' -> {
                val start = stack.removeLast()
                if (stack.size >= 4) {
                    val (int1, int2) = substring(start + 1, index).split(',').map { it.toInt() }
                    val left = substring(0, start).addToRightVal(int1)
                    val right = substring(index + 1).addToLeftVal(int2)
                    return "${left}0${right}" to true
                }
            }
        }
    }

    return this to false
}

fun SFN.split(): Pair<SFN, Boolean> {
    val match = """\d{2,}""".toRegex().findAll(this).firstOrNull()
    return if (match == null) this to false else {
        val left = match.value.toInt() / 2
        val right = match.value.toInt() / 2 + match.value.toInt() % 2
        replaceRange(match.range, "[$left,$right]") to true
    }
}

fun SFN.reduce(): SFN {
    return generateSequence(this to true) { (sfn, _) ->
        val exploded = sfn.explode()
        if (exploded.second) 
            exploded.first to true 
        else {
            val split = sfn.split()
            if (split.second) 
                split.first to true 
            else 
                sfn to false
        }
    }.first { !it.second }.first
}

val SFN.magnitude
    get() = generateSequence(this) { sfn ->
        sfn.replace("""\[(\d+),(\d+)]""".toRegex()) { match ->
            val (left, right) = match.groupValues.drop(1).map { it.toInt() }
            (3 * left + 2 * right).toString()
        }
    }.first { numberRegEx.matches(it) }.toInt()
