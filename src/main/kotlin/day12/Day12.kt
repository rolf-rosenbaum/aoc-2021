package day12

import Input
import readInput

typealias CaveMap = Map<String, List<String>>

fun main() {
    val testInput = readInput("day12/test_input")
    val input = readInput("day12/input")

    fun part1(input: Input): Int {
        caves = input.toCaveMap()
        return traverse(::allowedToVisit).size

    }

    fun part2(input: Input): Int {
        return traverse(::allowedToVisitPart2).size
    }

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 19)
    println(part1(input))

    check(part2(testInput) == 103)
    println(part2(input))
}

val foundPaths = mutableSetOf<String>()
var caves: CaveMap = mapOf()

private fun allowedToVisit(cave: String, pathSoFar: List<String>) = cave.all { it.isUpperCase() } || cave !in pathSoFar

private fun allowedToVisitPart2(cave: String, pathSoFar: List<String>) =
    when {
        cave.all { it.isUpperCase() } -> true
        cave == "start" -> false
        cave !in pathSoFar -> true
        else -> pathSoFar
            .filterNot { it.all { c -> c.isUpperCase() } }
            .groupBy { it }
            .none { it.value.size == 2 }
    }

fun traverse(
    allowedToVisit: (String, List<String>) -> Boolean,
    path: List<String> = listOf("start")
): List<List<String>> =
    if (path.last() == "end") listOf(path)
    else caves.getValue(path.last())
        .filter { allowedToVisit(it, path) }
        .flatMap {
            traverse(allowedToVisit, path + it)
        }

fun Input.toCaveMap() = map { it.split("-") }
    .flatMap {
        listOf(
            it.first() to it.last(),
            it.last() to it.first()
        )
    }
    .groupBy({ it.first }, { it.second })
 
