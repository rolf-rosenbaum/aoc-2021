package day15

import Input
import readInput
import java.time.LocalTime
import java.util.*

typealias Cave = Array<IntArray>


fun main() {

    fun part1(input: Input): Int {
        val start = LocalTime.now()
        val minRisk = input.toCave().findPath()
        val end = LocalTime.now()
        println("Duration : ${(end.toNanoOfDay() - start.toNanoOfDay()) / 100000} ms")
        return minRisk

    }

    fun part2(input: Input): Int {
        val cave = input.toCave()

        val start = LocalTime.now()
        val minRisk = cave.findPath(Point((cave.first().size * 5) - 1, (cave.size * 5) - 1), part1 = false)
        val end = LocalTime.now()

        println("Duration : ${(end.toNanoOfDay() - start.toNanoOfDay()) / 1000000} ms")
        return minRisk
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("day15/test_input")
    val input = readInput("day15/input")

    check(part1(testInput) == 40)
    println(part1(input))

    check(part2(testInput) == 315)
    println(part2(input))
}

data class Path(val pos: Point, val totalRisk: Int) : Comparable<Path> {
    override fun compareTo(other: Path): Int = totalRisk - other.totalRisk
}

fun Cave.getPart1(p: Point): Int = this[p.x][p.y]

fun Cave.getPart2(p: Point): Int {
    val dx = p.x / this.first().size
    val dy = p.y / this.size
    val originalRisk = this[p.y % this.size][p.x % this.first().size]
    val newRisk = (originalRisk + dx + dy)
    return newRisk.takeIf { it < 10 } ?: (newRisk - 9)
}

data class Point(val x: Int, val y: Int) {
    fun neighbors() = listOf(
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x, y + 1),
        Point(x, y - 1)
    )
}

fun Cave.findPath(destination: Point = Point(first().lastIndex, lastIndex), part1: Boolean = true): Int {

    val possiblePath = PriorityQueue<Path>().apply { add(Path(Point(0, 0), 0)) } // first cave bears no risk
    val beenHereBefore = mutableSetOf<Point>()

    while (possiblePath.isNotEmpty()) {
        val currentCave = possiblePath.poll()

        if (currentCave.pos == destination) return currentCave.totalRisk

        if (currentCave.pos !in beenHereBefore) {
            beenHereBefore.add(currentCave.pos)
            currentCave.pos.neighbors().filter {
                it.x in (0..destination.x) && it.y in (0..destination.y)
            }.forEach {
                possiblePath.offer(Path(it, currentCave.totalRisk + if (part1) getPart1(it) else getPart2(it)))
            }
        }
    }
    error("how did I get here???")
}

fun Input.toCave() = map { line ->
    line.map {
        it.digitToInt()
    }.toIntArray()
}.toTypedArray()