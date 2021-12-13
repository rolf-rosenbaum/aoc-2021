package day13

import Input
import Point
import readInput
import kotlin.math.abs


typealias Paper = Set<Point>

fun main() {

    fun part1(input: Input): Int {
        val paper = input.toPaper().print(h = 7)
        val folded1 = paper.foldHorizontally(7).print(v = 5)
        folded1.foldVertically(5).print()
        return 0
    }

    fun part2(input: Input): Int {
        input.toPaper()
            .foldVertically(655)
            .foldHorizontally(447)
            .foldVertically(327)
            .foldHorizontally(223)
            .foldVertically(163)
            .foldHorizontally(111)
            .foldVertically(81)
            .foldHorizontally(55)
            .foldVertically(40)
            .foldHorizontally(27)
            .foldHorizontally(13)
            .foldHorizontally(6)
            .print()

        return 0

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day13/test_input")
    val input = readInput("day13/input")

    part1(testInput)
    println(part1(input))

//    check(part2(testInput) == 0)
    println(part2(input))
}

fun Paper.print(h: Int? = null, v: Int? = null): Paper {
    (minOf { it.y }..maxOf { it.y }).forEach { y ->
        (minOf { it.x }..maxOf { it.x }).forEach { x ->
            if (h == y) {
                print("-")
            } else {
                if (v == x) {
                    print("|")
                } else {
                    print(
                        if (this.contains(Point(x, y))) {
                            "#"
                        } else {
                            " "
                        }
                    )
                }
            }

        }
        println()
    }
    println()
    return this
}

fun Paper.foldHorizontally(foldLine: Int): Paper = this
    .map { p ->
        Point(x = p.x, y = foldLine - abs(p.y - foldLine))
    }.toSet()

fun Paper.foldVertically(foldLine: Int): Paper = this
    .map { p ->
        Point(x = foldLine -abs(p.x - foldLine), y = p.y)
    }.toSet()

fun Input.toPaper(): Paper {
    val paper = mutableSetOf<Point>()
    dropLastWhile { it.startsWith("fold") }.dropLast(1)
        .map { line ->
            val c = line.split(",")
            paper.add(Point(c.first().toInt(), c.last().toInt()))
        }

    return paper
}
