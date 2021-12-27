package day23

import Input
import Point
import readInput
import java.util.*
import kotlin.math.abs

typealias Burrow = Map<Point, Amphipod>

val allowedPositions = setOf(
    Point(1, 1),
    Point(2, 1),
//    Point(3,1),
    Point(4, 1),
//    Point(5,1),
    Point(6, 1),
//    Point(7,1),
    Point(8, 1),
//    Point(9,1),
    Point(10, 1),
    Point(11, 1),
    Point(3, 2),
    Point(3, 3),
    Point(5, 2),
    Point(7, 3),
    Point(7, 3),
    Point(9, 3),
    Point(9, 3)
)

fun main() {

    fun part1(input: Input): Int {
        return 0
    }

    fun part2(input: Input): Int {
        return 0

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day23/test_input")
    val input = readInput("day23/input")

    check(part1(testInput) == 0)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}


fun solve(burrow: Burrow): Int {
    val possiblePath = PriorityQueue<WeightedPath>().apply { add(WeightedPath(burrow, 0)) } // no move yet

    while (possiblePath.isNotEmpty()) {
        val currentPath = possiblePath.poll()
        if (currentPath.burrow.allHome()) return currentPath.totalEnergy

        val nextMoves = currentPath.burrow.nextMoves()
    }
    return 0
}

fun Burrow.nextMoves(): List<Burrow> {
    val result = mutableListOf<Burrow>()
    return result
}

fun Burrow.nextMoveFor(point: Point): Burrow {
    val result = mutableMapOf<Point, Amphipod>()
    result.putAll(this)
    val amphipod = this[point]!!
    listOf(point.x + 1, point.x - 1).forEach { x ->
        listOf(point.y + 1, point.y - 1).forEach { y ->
            val p = Point(x, y)
            if (p in allowedPositions && this[p] == null) {
                result[p] = Amphipod(amphipod.colour, amphipod.steps + point.distanceTo(p))
                result.remove(point)
            }
        }
    }
    return result 
}

data class WeightedPath(val burrow: Burrow, val totalEnergy: Int) : Comparable<WeightedPath> {
    override fun compareTo(other: WeightedPath): Int = totalEnergy - other.totalEnergy
}

data class Amphipod(val colour: Char, val steps: Int = 0) {
    fun energyUsed() = when (colour) {
        'A' -> steps
        'B' -> steps * 10
        'C' -> steps * 100
        'D' -> steps * 1000
        else -> error("Amphipod with unknown color")
    }
}

fun Point.isDestinationFor(a: Amphipod) = when (a.colour) {
    'A' -> x == 3 && y in 2..3
    'B' -> x == 5 && y in 2..3
    'C' -> x == 7 && y in 2..3
    'D' -> x == 9 && y in 2..3
    else -> error("")
}

fun Point.distanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)

fun Burrow.allHome() = this.all { (p, a) -> p.isDestinationFor(a) }

fun Input.parse(): Burrow {
    val result = mutableMapOf<Point, Amphipod>()
    flatMapIndexed { x, line ->
        line.mapIndexed { y, c ->
            if (c.isLetter()) result[Point(x, y)] = Amphipod(c)
        }
    }
    return result
}
