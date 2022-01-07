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

fun List<Region>.solve() =
    fold<Region, MutableList<Region>>(mutableListOf()) { done, region ->
        (done + done.fold(if (region.on) mutableListOf(region) else emptyList()) { d, r ->
            (d + r.intersect(region, !r.on)).filterNotNull().toMutableList()
        }
                ).toMutableList()
    }.sumOf { it.volume() }

data class Region(val on: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {
    fun volume() = (x2 - x1 + 1L) * (y2 - y1 + 1L) * (z2 - z1 + 1L) * (if (!on) -1 else 1)

    fun intersect(other: Region, on: Boolean): Region? {
        return if (!intersects(other)) null
        else Region(
            on,
            max(x1, other.x1),
            min(x2, other.x2),
            max(y1, other.y1),
            min(y2, other.y2),
            max(z1, other.z1),
            min(z2, other.z2)
        )
    }

    private fun intersects(region: Region) =
        x1 <= region.x2 && x2 >= region.x1 && y1 <= region.y2 && y2 >= region.y1 && z1 <= region.z2 && z2 >= region.z1
}

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


fun IntRange.fix() = if (last < first) last..first else this
