package day22

import Input
import readInput
import kotlin.math.max
import kotlin.math.min


fun main() {

    fun part1(input: Input): Long =
        input.toRegions().take(20).solve()

    fun part2(input: Input): Long =
        input.toRegions().solve()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day22/test_input")
    val testInput2 = readInput("day22/test_input2")
    val input = readInput("day22/input")

    check(part1(testInput) == 590784L)
    println(part1(input))

    check(part2(testInput2) == 2758514936282235L)
    println(part2(input))
}

fun List<Region>.solve() = fold<Region, MutableList<Region>>(mutableListOf()) { acc, region ->
    val toAdd = mutableListOf<Region>()
    if (region.on) toAdd.add(region)
    acc.forEach { d ->
        d.intersect(region, !d.on)?.let<Region, Unit> {
            toAdd.add(it)
        }
    }
    (acc + toAdd).toMutableList()
}.sumOf { it.volume() }


fun Input.toRegions(): List<Region> = map {
    var on = false
    var xRange = 0..0
    var yRange = 0..0
    var zRange = 0..0
    it.split(" ").let { (first, rest) ->
        on = first == "on"
        rest.split(",").let { (xr, yr, zr) ->
            xRange = xr.split("=").let { (_, range) ->
                range.split("..").let { (first, second) ->
                    IntRange(first.toInt(), second.toInt()).fix()
                }
            }
            yRange = yr.split("=").let { (_, range) ->
                range.split("..").let { (first, second) ->
                    IntRange(first.toInt(), second.toInt()).fix()
                }
            }
            zRange = zr.split("=").let { (_, range) ->
                range.split("..").let { (first, second) ->
                    IntRange(first.toInt(), second.toInt()).fix()
                }
            }
        }
    }
    Region(on, xRange.first, xRange.last, yRange.first, yRange.last, zRange.first, zRange.last)
}

data class Region(val on: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {
    fun volume() = (x2 - x1 + 1L) * (y2 - y1 + 1L) * (z2 - z1 + 1L) * (if (!on) -1 else 1)

    fun intersect(c: Region, on: Boolean): Region? {
        return if (x1 > c.x2 || x2 < c.x1 || y1 > c.y2 || y2 < c.y1 || z1 > c.z2 || z2 < c.z1) null
        else Region(on, max(x1, c.x1), min(x2, c.x2), max(y1, c.y1), min(y2, c.y2), max(z1, c.z1), min(z2, c.z2))
    }
}

fun IntRange.fix() = if (last < first) last..first else this
