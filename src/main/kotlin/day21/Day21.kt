package day21

import Input
import readInput
import java.math.BigInteger


fun main() {
    
    fun Input.playDiceGame(winningScore: Int = 1000): Int {
        Die.reset()
        var p1Pos = startingPositions().first().toInt()
        var p2Pos = startingPositions().last().toInt()
        var p1Score = 0
        var p2Score = 0

        do {
            val p1Move = Die.roll() + Die.roll() + Die.roll()
            p1Pos = (p1Pos + p1Move).wrapAt(10)
            p1Score += p1Pos
            if (p1Score >= winningScore)
                return p2Score * Die.rolls

            val p2Move = Die.roll() + Die.roll() + Die.roll()
            p2Pos = (p2Pos + p2Move).wrapAt(10)
            p2Score += p2Pos
            if (p2Score >= winningScore)
                return p1Score * Die.rolls
        } while (true)
    }

    fun part1(input: Input): Int {
        return input.playDiceGame(1000)
    }

    fun part2(input: Input): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day21/test_input")
    val input = readInput("day21/input")

    check(part1(testInput) == 739785)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}

object Die {
    var rolls = 0
    private var score = 0
    fun reset() {
        rolls = 0
        score = 0
    }

    fun roll(): Int {
        rolls++
        score++
        return score.wrapAt(100)

    }
}

fun Input.startingPositions() = map { it.split(": ").last() }

fun Int.wrapAt(n: Int) = if (this % n == 0) n else this % n