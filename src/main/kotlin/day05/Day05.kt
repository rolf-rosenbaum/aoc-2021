package day05

import Input
import readInput
import kotlin.math.max
import kotlin.math.min

data class Coordinate(val x: Int, val y: Int)

data class Line(val start: Coordinate, val end: Coordinate) {
    val isHorizontal = start.y == end.y
    val isVertical = start.x == end.x
    val isTopLeftToBottomRightDiagonal = start.x - start.y == end.x - end.y
    private val isTopRightToBottomLeftDiagonal  = start.x + start.y == end.x + end.y
    val isDiagonal  = isTopLeftToBottomRightDiagonal || isTopRightToBottomLeftDiagonal
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    val input = readInput("day05/Day05")

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

fun part1(input: Input): Int {
    val field = mutableMapOf<Coordinate, Int>()
    readLines(input).map { line ->
        field.markVerticalOrHorizontalLine(line)
    }
    return field.count { it.value > 1 }
}

fun part2(input: Input): Int {
    val field = mutableMapOf<Coordinate, Int>()
    readLines(input).map { line ->
        field.markVerticalOrHorizontalLine(line)
        field.markDiagonalLine(line)
    }
    return field.count { it.value > 1 }
}

private fun MutableMap<Coordinate, Int>.markDiagonalLine(line: Line) {
    if (line.isDiagonal) markDiagonal(line)
}

private fun MutableMap<Coordinate, Int>.markVerticalOrHorizontalLine(line: Line) {
    if (line.isHorizontal) markHorizontal(line)
    if (line.isVertical) markVertical(line)
}

fun readLines(input: Input) = input.map { line ->
    val coordinate = line.split(" -> ")
    Line(
        Coordinate(coordinate.first().split(",").first().toInt(), coordinate.first().split(",").last().toInt()),
        Coordinate(coordinate.last().split(",").first().toInt(), coordinate.last().split(",").last().toInt())
    )
}

fun MutableMap<Coordinate, Int>.markHorizontal(line: Line) {
    xRange(line).forEach { x ->
        increaseCrossingCounter(x, line.start.y)
    }
}

fun MutableMap<Coordinate, Int>.markVertical(line: Line) {
    yRange(line).forEach { y ->
        increaseCrossingCounter(line.start.x, y)
    }
}

fun MutableMap<Coordinate, Int>.markDiagonal(line: Line) {
    xRange(line).forEach { x ->
        yRange(line).forEach { y ->
            if (line.isTopLeftToBottomRightDiagonal) {
                if (x - y == line.start.x - line.start.y) {
                    increaseCrossingCounter(x, y)
                }
            } else {
                if (x + y == line.start.x + line.start.y)
                    increaseCrossingCounter(x, y)
            }
        }
    }
}

private fun yRange(line: Line) = min(line.start.y, line.end.y)..max(line.start.y, line.end.y)

private fun xRange(line: Line) = min(line.start.x, line.end.x)..max(line.start.x, line.end.x)

private fun MutableMap<Coordinate, Int>.increaseCrossingCounter(x: Int, y: Int) {
    (this[Coordinate(x, y)] ?: 0).also { count -> this[Coordinate(x, y)] = count + 1 }
}
