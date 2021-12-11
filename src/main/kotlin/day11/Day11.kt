package day11

import Input
import Point
import readInput
import kotlin.math.max
import kotlin.math.min

typealias OctopusField = MutableSet<Octopus>

fun main() {

    fun part1(input: Input): Int {
        return generateSequence(input.toOctopusField()) {
            it.step()
        }.drop(100)
            .first()
            .sumOf { it.flashes }
    }

    fun part2(input: Input): Int {
        var step = 0
        generateSequence(input.toOctopusField()) {
            step++
            it.step()
        }.first {
            it.all { octopus -> octopus.energy % 10 == 0 }
        }
        return step
    }
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day11/test_input")
    val input = readInput("day11/input")

    check(part1(testInput) == 1656)
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}

fun OctopusField.step(): OctopusField {
    val next = map { octopus ->
        octopus.raiseEnergy()
    }.toMutableSet()

    var flashed: Boolean
    do {
        flashed = false
        val flashedOnes = next.filter {
            it.flashedThisTurn
        }
        flashedOnes.filterNot { it.flashedTheNeighbors }
            .forEach {
                next.flashNeighbors(it)
                next.remove(it)
                next.add(it.flashedTheNeighbors())
                flashed = true
            }
    } while (flashed)
    return next.map { it.reset() }.toMutableSet()
}

private fun OctopusField.flashNeighbors(it: Octopus) {
    neighborsOf(it).forEach { neighbor ->
        remove(neighbor)
        add(neighbor.raiseEnergy())
    }
}

fun OctopusField.asString(): String {
    var s = ""
    (0..maxOf { it.pos.x }).forEach { y ->
        (0..maxOf { it.pos.y }).forEach { x ->
            s += findOctopusAt(Point(x, y)).energy % 10
        }
        s += "\n"
    }
    s += "\n"
    return s
}

fun OctopusField.findOctopusAt(p: Point) = first { it.pos == p }
fun OctopusField.neighborsOf(o: Octopus): Set<Octopus> =
    (max(minOf { it.pos.x }, o.pos.x - 1)..min(maxOf { it.pos.x }, o.pos.x + 1)).flatMap { x ->
        (max(minOf { it.pos.y }, o.pos.y - 1)..min(maxOf { it.pos.y }, o.pos.y + 1)).map { y ->
            findOctopusAt(Point(x, y))
        }
    }.filterNot { it.pos == o.pos }.toSet()

fun Input.toOctopusField(): OctopusField {
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

    fun raiseEnergy() =
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



