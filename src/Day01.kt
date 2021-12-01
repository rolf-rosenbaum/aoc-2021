fun main() {

    fun Input.sumUp(windowSize: Int) = toInts()
        .windowed(windowSize)
        .zipWithNext()
        .count {
            it.first.sum() < it.second.sum()
        }

    fun part1(input: Input): Int {
        return input.sumUp(1)
    }

    fun part2(input: Input): Int {
        return input.sumUp(3)
            
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
