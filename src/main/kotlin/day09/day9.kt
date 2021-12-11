package day09

import Input
import Point
import readInput
import kotlin.math.absoluteValue

typealias HeightMap = Map<Point, Int>

fun main() {
    fun part1(input: Input): Int {
        val heightMap = input.toHeightMap()
        return input.lowPoints().sumOf{
            heightMap[it]!! + 1
        }
    }

    fun part2(input: Input): Int {
        val heightMap = input.toHeightMap()
        
        return input.lowPoints().map { 
            heightMap.basinFor(it)
        }.sortedBy { it.size }.takeLast(3)
            .map { it.size + 1 }
            .reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09/test_input")
    val input = readInput("day09/input")

    val part1 = part1(testInput)
    println(part1)
    check(part1 == 15)
    println(part1(input))

    check(part2(testInput) == 1134)
    println(part2(input))
}
fun Input.lowPoints() =
    toHeightMap().let { heightMap ->
        heightMap.filter {
            heightMap.neighborsOf(it.key).all { point-> it.value < heightMap[point]!! }
        }.keys
    }

fun Input.toHeightMap(): HeightMap {
    val heightMap = mutableMapOf<Point, Int>()
    forEachIndexed { y, line ->
        line.toCharArray().mapIndexed { x, num ->
            heightMap[Point(x, y)] = num.toString().toInt()
        }
    }
    return heightMap
}

fun HeightMap.basinFor(point: Point): Set<Point> {
    return neighborsOf(point)
        .filterNot { this[it] == 9 }
        .filter {
        this[it]!! > this[point]!!
    }.let {
        it + it.map { neighbor -> 
            basinFor(neighbor)
        }.flatten()
    }.toSet()
}

fun HeightMap.neighborsOf(p: Point) =
    keys.filter {
        it.y == p.y && (it.x - p.x).absoluteValue == 1
                || it.x == p.x && (it.y - p.y).absoluteValue == 1
    }.map {
        it
    }
