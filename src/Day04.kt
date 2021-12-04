import java.awt.Point

typealias Board = List<BingoNumber>

fun main() {
    fun readBoards(input: List<String>): Board = input.filter { it.length > 2 }
        .flatMapIndexed { y, line ->
            line.split(" ")
                .filterNot { it.isEmpty() }
                .mapIndexed { x, num ->
                    BingoNumber(num.trim().toInt(), Point(x, y + 1))
                }
        }

    fun part1(input: Input): Int {
        var boards = readBoards(input.drop(2)).chunked(25)
        val numbers = input.first().split(",").map {
            it.toInt()
        }
        numbers.forEach { drawn ->
            boards = boards.map { board ->
                board.map { if (it.num == drawn) it.copy(marked = true) else it }
            }
            val winnerBoard = boards.firstOrNull {it.isWinning()} 
            if ( winnerBoard != null) {
                return winnerBoard.filterNot { it.marked }.sumOf { it.num } * drawn
            }
        }
        return 0
    }

    fun part2(input: Input): Int {
        var boards = readBoards(input.drop(2)).chunked(25).toMutableList()
        val numbers = input.first().split(",").map {
            it.toInt()
        }
        numbers.forEach { drawn ->
            boards = boards.map { board ->
                board.map { if (it.num == drawn) it.copy(marked = true) else it }
            }.toMutableList()
            boards.removeIf { it.isWinning() && boards.size > 1 }
            if (boards.size == 1 && boards.first().isWinning())
                return boards.first().filterNot { it.marked }.sumOf { it.num } * drawn
        }
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

fun Board.isWinning() = hasWinningRow() || hasWinningColumn()

fun Board.hasWinningRow(): Boolean = (1..5)
    .any { row ->
        count { it.position.y % 5 == row && it.marked } == 5
    }

fun Board.hasWinningColumn(): Boolean = filter { it.marked }
    .groupBy { it.position.x }.any {it.value.size == 5}

class Point(val x: Int, val y: Int)

data class BingoNumber(
    val num: Int,
    val position: Point,
    val marked: Boolean = false
)

