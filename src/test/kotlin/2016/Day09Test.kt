package `2016`

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun testPart1() {
        Assertions.assertEquals("ABCBCDEFEFG", part1("A(2x2)BCD(2x2)EFG"))
        Assertions.assertEquals("X(3x3)ABC(3x3)ABCY", part1("X(8x2)(3x3)ABCY"))
        Assertions.assertEquals("ADVENT", part1("ADVENT"))
        Assertions.assertEquals("ABBBBBC", part1("A(1x5)BC"))
        Assertions.assertEquals("XYZXYZXYZ", part1("(3x3)XYZ"))
    }

    @Test
    fun testPart2() {
        
        Assertions.assertEquals(445, part2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"))
        Assertions.assertEquals("XABCABCABCABCABCABCY".length, part2("X(8x2)(3x3)ABCY"))
    }


}