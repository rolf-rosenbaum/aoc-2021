package day18

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {
    
    
    @Test
    fun `adding SFNs`() {
        
        val first = SFN(leftVal = 1, rightVal = 2)
        val second = SFN(leftVal = 1, rightVal = 2)
        
        val third  = first + second
        val fourth = third + second
        Assertions.assertEquals("[[1,2],[[[1,2],[1,2]],[1,2]]]", (first + fourth).toString())
    }

    @Test
    fun `parse SFN`() {
        val sfn = "[1,2]".toSFN()
        Assertions.assertEquals("[1,2]", sfn.toString())

        val sfn2 = "[3,[1,2]]".toSFN()
        Assertions.assertEquals("[1,2]", sfn2.toString())

        val sfn3 = "[[1,2],[3,4]]".toSFN(SFN())
        Assertions.assertEquals("[[1,2],[3,4]]", sfn3.toString())
    }
}