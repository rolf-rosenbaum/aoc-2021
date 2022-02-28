package `2016`

import day15.Point
import readInput

typealias Display = MutableSet<Point>

private const val DISPLAY_WIDTH = 50
private const val DISPLAY_HEIGHT = 6

fun main() {
    val display = mutableSetOf<Point>()

    fun part1(input: List<String>): Int {
        input.forEach {
            when {
                it.contains("rect") -> {
                    val (x, y) = it.split(" ").last().split("x").map { it1 -> it1.toInt() }
                    display.rect(x, y)
                }
                it.contains("row") -> {
                    val by = it.split(" ").last().toInt()
                    val y = it.split("=").last().split(" ").first().toInt()
                    display.rotateRow(y = y, shift = by)
                }
                it.contains("column") -> {
                    val by = it.split(" ").last().toInt()
                    val x = it.split("=").last().split(" ").first().toInt()
                    display.rotateColumn(x = x, shift = by)
                }
            }
        }

        (0 until DISPLAY_HEIGHT).forEach { y ->
            (0 until DISPLAY_WIDTH).forEach { x ->
                if (display.contains(Point(x, y))) print("#") else print(" ")
            }
            println()
        }
        return display.size

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("2016/day08_test_input")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("2016/day08_input")
    println(part1(input))
    println(part2(input))

}

fun Display.rect(x: Int, y: Int) {
    (0 until y).forEach { y ->
        (0 until x).forEach { x ->
            this.add(Point(x, y))
        }
    }
}

fun Display.rotateRow(y: Int, shift: Int) {
    val newRow = mutableSetOf<Point>()
    (0 until DISPLAY_WIDTH).forEach { x ->
        val point = Point(x, y)
        if (contains(point)) {
            remove(point)
            newRow.add(Point((x + shift) % DISPLAY_WIDTH, y))
        }
    }
    for (point in newRow) {
        add(point)
    }
}

fun Display.rotateColumn(x: Int, shift: Int) {
    val newRow = mutableSetOf<Point>()
    (0 until DISPLAY_HEIGHT).forEach { y ->
        val point = Point(x, y)if (contains(point)) {
            remove(point)
            newRow.add(Point(x, (y + shift) % DISPLAY_HEIGHT))
        }
    }
    for (point in newRow) {
        add(point)
    }
}
