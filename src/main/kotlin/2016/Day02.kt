package `2016`

import readInput


fun main() {
    fun part1(input: List<String>): String {
        fun Int.up() = if (this > 0) this - 1 else 0
        fun Int.down() = if (this < 2) this + 1 else 2
        fun Int.left() = if (this > 0) this - 1 else 0
        fun Int.right() = if (this < 2) this + 1 else 2
        val keypad = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9)
        )
        var x = 1
        var y = 1
        val numbers = input.map { line ->
            line.map {
                when (it) {
                    'U' -> y = y.up()
                    'D' -> y = y.down()
                    'L' -> x = x.left()
                    'R' -> x = x.right()
                }
            }
            keypad[y][x]
        }
        return numbers.joinToString("")
    }

    fun part2(input: List<String>): String {
        val keypad = listOf(
            listOf(" ", " ", "1", " ", " "),
            listOf(" ", "2", "3", "4", " "),
            listOf("5", "6", "7", "8", "9"),
            listOf(" ", "A", "B", "C", " "),
            listOf(" ", " ", "D", " ", " ")
        )
        var x = 0
        var y = 2
        fun up() { if (y > 0 && keypad[y - 1][x] != " ") y-- }

        fun down() { if (y < 4 && keypad[y + 1][x] != " ") y++ }

        fun left() { if (x > 0 && keypad[y][x - 1] != " ") x-- }

        fun right() { if (x < 4 && keypad[y][x + 1] != " ") x++ }

        val numbers = input.map { line ->
            line.map {
                when (it) {
                    'U' -> up()
                    'D' -> down()
                    'L' -> left()
                    'R' -> right()
                }
            }
            keypad[y][x]
        }
        return numbers.joinToString("")
    }

    val testInput = readInput("2016/day02_test_input")
    check(part1(testInput) == "1985")
    check(part2(testInput) == "5DB3")

    val input = readInput("2016/day02_input")
    println(part1(input))
    println(part2(input))
}

