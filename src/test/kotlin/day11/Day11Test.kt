package day11

import Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

@Suppress("FunctionName")
class Day11Test {

    @Test
    fun neighbors() {
        val field = readInput("day11/test_input").toOctopusField()
        assertEquals(3, field.neighborsOf(Octopus(Point(0, 0), 0)).size)

        val neighborsOf = field.neighborsOf(Octopus(Point(5, 5), 0))
        assertEquals(8, neighborsOf.size)

    }

    @Test
    fun `raise energy`() {
        val octopus = Octopus(pos = Point(3, 3), energy = 5).raiseEnergy()
        assertEquals(Octopus(Point(3, 3), energy = 6), octopus)

        val octopus2 = Octopus(pos = Point(3, 3), energy = 9).raiseEnergy()
        assertEquals(Octopus(Point(3, 3), energy = 0,  flashes = 1), octopus2)

        val octopus3 = Octopus(Point(3, 3), energy = 0,  flashes = 1).raiseEnergy()
        assertEquals(Octopus(Point(3, 3), energy = 0,  flashes = 1), octopus3)
    }

    @Test
    fun `next generation`() {
        val field = readInput("day11/test_input2").toOctopusField()
        val expexcted = readInput("day11/expectation").joinToString("\n")

        val next = field.step()
        assertEquals(expexcted.trim(), next.asString().trim())
        
        
        
    }
}