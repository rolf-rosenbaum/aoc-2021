import kotlin.math.max
import kotlin.math.min

data class Coordinate(val x: Int, val y: Int)

data class Line(val start: Coordinate, val end: Coordinate) {
    fun isHorizontal() = start.y == end.y
    fun isVertical() = start.x == end.x
    fun isDiagonal() = isTopLeftToBottomRightDiagonal() || isTopRightToBottomLeftDiagonal()
    fun isTopLeftToBottomRightDiagonal() = start.x - start.y == end.x - end.y
    fun isTopRightToBottomLeftDiagonal() = start.x + start.y == end.x + end.y
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

fun part1(input: Input): Int {
    val lines = readLines(input)
    val field = mutableMapOf<Coordinate, Int>()
    lines
        .map { line ->
            field.markVerticalAnHorizontalLines(line)
        }
    return field.count { it.value > 1 }
}

fun part2(input: Input): Int {
    val lines = readLines(input)
    val field = mutableMapOf<Coordinate, Int>()
    lines
        .map { line ->
            field.markVerticalAnHorizontalLines(line)
            field.markDiagonalLines(line)
        }
    return field.count { it.value > 1 }
}

private fun MutableMap<Coordinate, Int>.markDiagonalLines(line: Line) {
    if (line.isDiagonal()) markDiagonal(line)
}

private fun MutableMap<Coordinate, Int>.markVerticalAnHorizontalLines(line: Line) {
    if (line.isHorizontal()) markHorizontal(line)
    if (line.isVertical()) markVertical(line)
}

fun readLines(input: Input) = input.map { line ->
    val coordinate = line.split(" -> ")
    val start = Coordinate(coordinate.first().split(",").first().toInt(), coordinate.first().split(",").last().toInt())
    val end = Coordinate(coordinate.last().split(",").first().toInt(), coordinate.last().split(",").last().toInt())
    Line(start, end)
}

fun MutableMap<Coordinate, Int>.markHorizontal(line: Line) {
    (min(line.start.x, line.end.x)..max(line.start.x, line.end.x)).forEach { x ->
        increaseCrossingCounter(x, line.start.y)
    }
}

fun MutableMap<Coordinate, Int>.markVertical(line: Line) {
    (min(line.start.y, line.end.y)..max(line.start.y, line.end.y)).forEach { y ->
        increaseCrossingCounter(line.start.x, y)
    }
}

fun MutableMap<Coordinate, Int>.markDiagonal(line: Line) {
    if (line.isTopLeftToBottomRightDiagonal()) {
        (min(line.start.x, line.end.x)..max(line.start.x, line.end.x)).forEach { x ->
            (min(line.start.y, line.end.y)..max(line.start.y, line.end.y)).forEach { y ->
                if (x - y == line.start.x - line.start.y)
                    increaseCrossingCounter(x, y)
            }
        }
    }

    if (line.isTopRightToBottomLeftDiagonal()) {
        (min(line.start.x, line.end.x)..max(line.start.x, line.end.x)).forEach { x ->
            (min(line.start.y, line.end.y) .. max(line.start.y, line.end.y)).forEach { y ->
                if (x + y == line.start.x + line.start.y)
                    increaseCrossingCounter(x, y)
            }
        }
    }
}

private fun MutableMap<Coordinate, Int>.increaseCrossingCounter(x: Int, y: Int) {
    ((this[Coordinate(x, y)] ?: 0) + 1).also { this[Coordinate(x, y)] = it }
}
