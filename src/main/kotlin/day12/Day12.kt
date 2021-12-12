package day12

import Input
import Point
import readInput
import kotlin.math.max
import kotlin.math.min

typealias `Octopus's Garden` = MutableSet<Octopus>

fun main() {

    fun part1(input: Input): Int {
        return generateSequence(input.`to Octopus's Garden`()) {
            it.step()
        }.drop(100)
            .first()
            .sumOf { it.flashes }
    }

    fun part2(input: Input): Int {
        var step = 0
        generateSequence(input.`to Octopus's Garden`()) {
            step++
            it.step()
        }.first { it.all { octopus -> octopus.energy % 10 == 0 } }
        return step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day12/test_input")
    val input = readInput("day12/input")

    check(part1(testInput) == 1656)
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}

fun `Octopus's Garden`.step(): `Octopus's Garden` {
    val next = map { octopus ->
        octopus.beWarm()
    }.toMutableSet()

    var flashed: Boolean
    do {
        flashed = false
        val flashedOnes = next.filter {
            it.flashedThisTurn
        }
        flashedOnes.filterNot { it.flashedTheNeighbors }
            .forEach {
                next.flashTheNeighbors(it)
                next.remove(it)
                next.add(it.flashedTheNeighbors())
                flashed = true
            }
    } while (flashed)
    return next.map { it.reset() }.toMutableSet()
}

private fun `Octopus's Garden`.flashTheNeighbors(it: Octopus) {
    neighborsOf(it).forEach { neighbor ->
        remove(neighbor)
        add(neighbor.beWarm())
    }
}

fun `Octopus's Garden`.asString(): String {
    var s = ""
    (0..maxOf { it.pos.x }).forEach { y ->
        (0..maxOf { it.pos.y }).forEach { x ->
            s += findOctopusBelowTheStorm(Point(x, y)).energy % 10
        }
        s += "\n"
    }
    s += "\n"
    return s
}

fun `Octopus's Garden`.findOctopusBelowTheStorm(p: Point) = first { it.pos == p }
fun `Octopus's Garden`.neighborsOf(o: Octopus): Set<Octopus> =
    (max(minOf { it.pos.x }, o.pos.x - 1)..min(maxOf { it.pos.x }, o.pos.x + 1)).flatMap { x ->
        (max(minOf { it.pos.y }, o.pos.y - 1)..min(maxOf { it.pos.y }, o.pos.y + 1)).map { y ->
            findOctopusBelowTheStorm(Point(x, y))
        }
    }.filterNot { it.pos == o.pos }.toSet()

fun Input.`to Octopus's Garden`(): `Octopus's Garden` {
    return flatMapIndexed { y, line ->
        line.mapIndexed { x, num ->
            Octopus(pos = Point(x, y), energy = num.toString().toInt())
        }
    }.toMutableSet()
}

data class Octopus(
    val pos: Point,
    val energy: Int,
    val flashes: Int = 0,
    val flashedTheNeighbors: Boolean = false
) {
    val flashedThisTurn = energy == 10

    fun beWarm() =
        if (flashedThisTurn) {
            this
        } else if (energy == 9) {
            copy(energy = 10, flashes = flashes + 1)
        } else {
            copy(energy = energy + 1)
        }

    fun flashedTheNeighbors() = copy(flashedTheNeighbors = true)
    fun reset() = copy(flashedTheNeighbors = false, energy = energy % 10)
}



