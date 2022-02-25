package `2016`

import readInput
import java.math.BigInteger
import java.security.MessageDigest


fun main() {
    fun part1(input: String = "reyedfim"): String {
        
        var passwd = ""
        var i = 0
        while (passwd.length < 8) {
            val foo = (input + 1).md5()
            if (foo.startsWith("00000")) 
                passwd += foo[5]
            i++
        }
        
        println(passwd)
        
        return passwd
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("2016/day04_test_input")
    check(part1("abc") == "18f47a30")
//    check(part2(testInput) == 0)

    val input = readInput("2016/day04_input")
    println(part1())
    println(part2(input))
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}