package `2016`

import readInput


fun main() {

    fun part1(input: List<String>): Int {
        return input.count { it.supportsTLS() }
    }


    fun part2(input: List<String>): Int {
        return input.count { it.supportsSSL() }
    }


    val testInput = readInput("2016/day07_test_input")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 1)

    val input = readInput("2016/day07_input")
    println(part1(input))
    println(part2(input))
}

fun String.supportsTLS(): Boolean = this.toOutSideAndInsideOfBrackets().let {
    it.first.containsPairAndReversePair() && !it.second.containsPairAndReversePair()
}

fun String.supportsSSL(): Boolean = this.toOutSideAndInsideOfBrackets().let { pair ->
    val abas = pair.first.flatMap { it.toABAs() }.filterNot { it.isEmpty() }
    val babs = pair.second.flatMap { it.toABAs() }.filterNot { it.isEmpty() }
    abas.any { aba -> 
        babs.contains(aba.toBAB()) 
    }
}

private fun String.toABAs(): List<String> =
    this.windowed(3).map { if (it.length == 3 && it.first() == it.last() && it[1] != it.first()) it else ""}

fun String.toBAB() = this[1].toString() + this.first() + this[1]

fun String.toOutSideAndInsideOfBrackets(): Pair<List<String>, List<String>> {
    val s = this.split("[", "]")
    val inside = mutableListOf<String>()
    val outside = mutableListOf<String>()

    s.indices.forEach { index ->
        if (index % 2 == 0) {
            outside += s[index]
        } else {
            inside += s[index]
        }
    }
    return outside to inside
}

fun List<String>.containsPairAndReversePair(): Boolean {
    return this.flatMap { s ->
        s.windowed(4) {
            it[0] != it[1] && it.substring(0, 2) == it.substring(2, 4).reversed()
        }

    }.any { it }

}
