package day02

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val pos = input.map {
            it.toMove()
        }.fold(Position(0, 0, 0)) { pos: Position, move -> move.execute(pos) }
        return pos.fw * pos.depth
    }

    fun part2(input: List<String>): Int {
        val pos = input.map {
            it.toMove()
        }.fold(Position(0, 0, 0)) { pos: Position, move -> move.executeSophisticated(pos) }
        return pos.fw * pos.depth
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun String.toMove(): Move {
    val tmp = split(" ")
    return Move(direction = Direction.fromString(tmp.first()), steps = tmp[1].toInt())
}

data class Move(val direction: Direction, val steps: Int) {
    fun execute(pos: Position) = when (direction) {
        Direction.UP -> Position(pos.fw, pos.depth - steps, pos.aim)
        Direction.DOWN -> Position(pos.fw, pos.depth + steps, pos.aim)
        Direction.FORWARD -> Position(pos.fw + steps, pos.depth, pos.aim)
    }

    fun executeSophisticated(pos: Position) = when (direction) {
        Direction.UP -> Position(pos.fw, pos.depth, pos.aim - steps)
        Direction.DOWN -> Position(pos.fw, pos.depth, pos.aim + steps)
        Direction.FORWARD -> Position(pos.fw + steps, pos.depth + (pos.aim * steps), pos.aim)
    }

}

data class Position(val fw: Int, val depth: Int, val aim: Int)

enum class Direction() {
    UP, DOWN, FORWARD;

    companion object {
        fun fromString(s: String) = when (s) {
            "up" -> UP
            "down" -> DOWN
            "forward" -> FORWARD
            else -> throw IllegalArgumentException("invalid direction: $s")
        }
    }
}