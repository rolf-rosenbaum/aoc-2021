package day25

import Input
import Point
import day25.Cucumber.EAST
import day25.Cucumber.SOUTH
import readInput

typealias Field = Map<Point, Cucumber>

var maxX = 0
var maxY = 0

fun main() {
    fun part1(input: Input): Int {
        maxX = input.first().length - 1
        maxY = input.size - 1
        return generateSequence(input.toCucumberField() to true) { (field, _) ->
            val f = field.step(EAST, Point::nextEast)
            f.first.step(SOUTH, Point::nextSouth, f.second)
        }.indexOfFirst { !it.second }
    }

    fun part2(input: Input): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day25/test_input")
    val input = readInput("day25/input")

    check(part1(testInput) == 58)
    println("success")
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}

fun Point.nextEast() = Point(x = if ((x + 1) > maxX) 0 else x + 1, y = y)
fun Point.nextSouth() = Point(x = x, y = if ((y + 1) > maxY) 0 else y + 1)

fun Field.step(direction: Cucumber, nextPos: (Point)->Point, moved: Boolean = false): Pair<Field, Boolean> {
    val resultEast = mutableMapOf<Point, Cucumber>()
    var m = moved 

    forEach { (p, cucumber) ->
        val next = nextPos.invoke(p)
        if (this[p] == direction && !this.containsKey(next)) {
            resultEast[next] = cucumber
            m = true
        } else {
            resultEast[p] = cucumber
        }
    }
    return resultEast to m
}

fun Input.toCucumberField(): Field {
    val result = mutableMapOf<Point, Cucumber>()

    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '>') result[Point(x, y)] = EAST
            if (c == 'v') result[Point(x, y)] = SOUTH
        }
    }
    return result

}

enum class Cucumber { EAST, SOUTH }
