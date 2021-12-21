package day20

import readInput

typealias Image = Array<Array<Boolean>>

var image: Image = arrayOf()
var algorithm: Array<Boolean> = arrayOf()

fun Image.getAt(x: Int, y: Int, infinity: Boolean): Boolean {
    return this.getOrNull(y)?.getOrNull(x) ?: infinity
}

fun Image.indexForAlgorithm(x: Int, y: Int, infinity: Boolean): Int {
    val replacement = if (algorithm[0]) infinity else false
    var result = 0

    if (this.getAt(x - 1, y - 1, replacement)) result += 256
    if (this.getAt(x, y - 1, replacement)) result += 128
    if (this.getAt(x + 1, y - 1, replacement)) result += 64
    if (this.getAt(x - 1, y, replacement)) result += 32
    if (this.getAt(x, y, replacement)) result += 16
    if (this.getAt(x + 1, y, replacement)) result += 8
    if (this.getAt(x - 1, y + 1, replacement)) result += 4
    if (this.getAt(x, y + 1, replacement)) result += 2
    if (this.getAt(x + 1, y + 1, replacement)) result += 1

    return result
}

fun main() {
    val testInput = readInput("day20/test_input")
    val input = readInput("day20/input")

    parseInput(testInput)
    check(part1() == 35)
    check(part2() == 3351)

    parseInput(input)
    println(part1())
    println(part2())

}

fun parseInput(input: List<String>) {
    algorithm = input[0].map { it == '#' }.toTypedArray()

    image = input.drop(2).map { row ->
        row.map { it == '#' }.toList().toTypedArray()
    }.toTypedArray()
}

fun enhance(infinity: Boolean) {
    val maxY = image.size
    val maxX = image[0].size

    image = (-2..maxY + 1).map { y ->
        (-2..maxX + 1).map { x ->
            algorithm[image.indexForAlgorithm(x, y, infinity)]
        }.toTypedArray()
    }.toTypedArray()
}

fun part1(): Int {
    repeat(2) {
        enhance(it % 2 != 0)
    }

    val result = image.sumOf { row ->
        row.count { it }
    }

    return result
}

fun part2(): Int {
    repeat(48) {
        enhance(it % 2 != 0)
    }

    val result = image.sumOf { row ->
        row.count { it }
    }

    return result
}