package day24

import Input
import readInput

fun main() {

    fun part1(input: Input): Long {
        val alu = ALU(instructions = input)
        val result = generateSequence(99999999999999 to false) { n ->
            if (n.first.toString().none { c -> c == '0' }) {
                (n.first - 1L) to alu.isValid(n.first.toString())
            } else n.first - 1 to false
        }.first { it.second }

        return result.first
    }

    fun part2(input: Input): Int {
        return 0

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day24/test_input")
    val input = readInput("day24/input")

    check(part1(testInput) == 13579246899999L)
    println(part1(input))

    check(part2(testInput) == 0)
    println(part2(input))
}


class ALU(var w: Int = 0, var x: Int = 0, var y: Int = 0, var z: Int = 0, private val instructions: Input) {

    fun process(num: Int, a: Int, b: Int, c: Int) {
        w = num
        x = z % 26 + b
        z /= a
        y = 25
        y = (if (x == w) 0 else 25) + 1
        z *= y
        y = (w + c) * x
        z += y
    }


    fun isValid(number: String): Boolean {
        var index = 0
        instructions.forEach { instruction ->
            instruction.split(" ").let { op ->
                when (op.first()) {
                    "inp" -> {
                        inp(number[index++].digitToInt())
                    }
                    "add" -> {
                        add(op[1].first(), op[2].first())
                    }
                    "mul" -> {
                        mul(op[1].first(), op[2].first())
                    }
                    "div" -> {
                        div(op[1].first(), op[2].first())
                    }
                    "mod" -> {
                        mod(op[1].first(), op[2].first())
                    }
                    "eql" -> {
                        eql(op[1].first(), op[2].first())
                    }
                }
            }
        }
        return z == 0
    }


    private fun inp(n: Int) {
        w = n
    }

    private fun add(a: Char, b: Char) {
        var n = 0
        n = valueOf(b)
        when (a) {
            'w' -> w += n
            'x' -> x += n
            'y' -> y += n
            'z' -> z += n
            else -> error("non existing variable: $a")
        }
    }

    private fun mul(a: Char, b: Char) {
        var n = 0
        n = if (b.isDigit()) b.digitToInt()
        else valueOf(b)
        when (a) {
            'w' -> w *= n
            'x' -> x *= n
            'y' -> y *= n
            'z' -> z *= n
            else -> error("non existing variable: $a")
        }
    }

    private fun div(a: Char, b: Char) {
        var n = 0
        n = if (b.isDigit()) b.digitToInt()
        else valueOf(b)
        when (a) {
            'w' -> w /= n
            'x' -> x /= n
            'y' -> y /= n
            'z' -> z /= n
            else -> error("non existing variable: $a")
        }
    }

    private fun mod(a: Char, b: Char) {
        var n = 0
        n = if (b.isDigit()) b.digitToInt()
        else valueOf(b)
        when (a) {
            'w' -> w %= n
            'x' -> x %= n
            'y' -> y %= n
            'z' -> z %= n
            else -> error("non existing variable: $a")
        }
    }

    private fun eql(a: Char, b: Char) {
        var n = 0
        n = if (b.isDigit()) b.digitToInt()
        else valueOf(b)
        when (a) {
            'w' -> w = if (w == n) 1 else 0
            'x' -> x = if (x == n) 1 else 0
            'y' -> y = if (y == n) 1 else 0
            'z' -> z = if (z == n) 1 else 0
            else -> error("non existing variable: $a")
        }
    }

    private fun valueOf(b: Char) = if (b.isDigit()) b.digitToInt() else when (b) {
        'w' -> w
        'x' -> x
        'y' -> y
        'z' -> z
        else -> error("non existing variable: $b")
    }


}
