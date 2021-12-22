package day17

import Point


fun main() {

    fun part1(input: TargetRange): Int {
        return maxHeights(input).maxOf{ it }
    }
    fun part2(input: TargetRange): Int = maxHeights(input).size

    // test if implementation meets criteria from the description, like:
    val testInput = TargetRange(20..30, -5 downTo -10)
    val input = TargetRange((211..232), -69 downTo -124)

    check(part1(testInput) == 45)
    println(part1(input))

    check(part2(testInput) == 112)
    println(part2(input))
}
fun maxHeights(input: TargetRange): List<Int> {
    val flatMap = (300 downTo -200).flatMap { x ->
        (200 downTo -200).mapNotNull { y ->
            val maxProbeHeight = maxProbeHeight(initialVelocity = Velocity(x, y), input)
            maxProbeHeight
        }
    }
    return flatMap
}

fun maxProbeHeight(initialVelocity: Velocity, targetRange: TargetRange): Int? {
    return  generateSequence(Trajectory(velocity = initialVelocity)) { t ->
        val step = t.step()
        step
    }.takeWhile {
        it.position.x <= targetRange.xRange.last && it.position.y >= targetRange.yRange.last
    }.let { t ->
        if (t.any { t -> t in targetRange }) {
            t.maxOfOrNull { t ->
                t.position.y 
            }
        } else null  
    }
}

fun Trajectory.step(): Trajectory {
    val xVelocity = if (velocity.x == 0) 0 else if (velocity.x > 0) velocity.x - 1 else velocity.x + 1
    return Trajectory(
        position = Point(
            position.x + velocity.x,
            position.y + velocity.y
        ),
        velocity = Velocity(xVelocity, velocity.y - 1)
    )
}

private fun IntProgression.fix() = if (last < first) last..first else this 

class Trajectory(val position: Point = Point(0, 0), val velocity: Velocity) {}
data class Velocity(val x: Int, val y: Int)

class TargetRange(val xRange: IntProgression, val yRange: IntProgression) {
    operator fun contains(t: Trajectory): Boolean {
        return t.position.x in xRange.fix() && t.position.y in yRange.fix()
    }

}