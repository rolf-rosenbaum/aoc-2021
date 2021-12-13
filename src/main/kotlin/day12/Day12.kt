package day12

import Input
import readInput

typealias CaveMap = MutableMap<Cave, Array<Cave>>

fun main() {

    fun part1(input: Input): Int {
        val caveMap = input.toCaveMap()
        caveMap.findAllPaths(Cave("start"), "", mutableSetOf())
        return foundPaths.size
    }

    fun part2(input: Input): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day12/test_input")
    val input = readInput("day12/input")

    check(part1(testInput) == 19)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}

val foundPaths = mutableSetOf<String>()

data class Cave(val id: String, val visited: Boolean = false) {
    val isSmallCave = id.all { it.isLowerCase() }
    val isStart = id == "start"
    val isEnd = id == "end"
    override fun equals(other: Any?): Boolean {
        return other is Cave && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun CaveMap.findAllPaths(
    currentCave: Cave,
    currentPath: String,
    visitedSmallCaves: MutableSet<Cave>
): String {
    if (currentCave.isSmallCave) {
        visitedSmallCaves.add(currentCave)
    }
    if (currentCave.isStart) {
        this[currentCave]!!
            .forEach {
                return findAllPaths(it, "start", visitedSmallCaves)
            }
    } else if (currentCave.isEnd) {
        foundPaths.add("$currentPath-end")
        return "$currentPath-end"
    } else {
        this[currentCave]!!.filterNot { visitedSmallCaves.contains(it) }.forEach {
            return findAllPaths(it, currentPath + "-" + it.id, visitedSmallCaves)
        }
    }
    return ""
}

fun Input.toCaveMap(): CaveMap {
    val caves: CaveMap = mutableMapOf()

    forEach { line ->
        val path = line.split("-")
        val cave1 = Cave(path.first())
        val cave2 = Cave(path.last())

        val neighbors1 = caves[cave1] ?: emptyArray()
        caves[cave1] = neighbors1 + cave2
        val neighbors2 = caves[cave2] ?: emptyArray()
        caves[cave2] = neighbors2 + cave1

    }
    return caves
} 
 
