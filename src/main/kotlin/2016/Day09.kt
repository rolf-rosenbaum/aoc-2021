package `2016`

import readInput

fun part1(input: String): String {
    val uncompressed = decompress(input)
    return uncompressed
}

fun decompress(seq: String): String {
    val marker = seq.nextMarker() ?: return seq
    val pos = seq.indexOf(marker)
    val (len, times) = marker.drop(1).dropLast(1).split("x").map(String::toInt)
    var decomp = ""
    repeat(times) {decomp += seq.substring(pos + marker.length, pos + marker.length + len) }
    val rest = seq.substring(pos + len + marker.length)
    return seq.substring(0, pos) + decomp + decompress(rest)
}

fun String.decompress2(total: Int = 0): Int {
    val marker = nextMarker() ?: return total + length
    val pos = indexOf(marker)
    val (len, times) = marker.drop(1).dropLast(1).split("x").map(String::toInt)
    var decomp = ""
    
    repeat(times) {
        decomp += substring(pos + marker.length, pos + marker.length + len) 
    }
    val rest = substring(pos + len + marker.length)

    if (decomp.contains("(")) {
        return total + substring(0, pos).length + decomp.decompress2(total)
    } else {
        return total + substring(0, pos).length + decomp.length + rest.decompress2(total)
    }
}

fun part2(input: String): Int {
    return input.decompress2(0)
}

fun main() {

    val testInput = readInput("2016/day09_test_input").first()
    val input = readInput("2016/day09_input").first()
    check(part1(testInput).length == 18)
    println(part1(input).length)

    check(part2(testInput) == 20)
    println(part2(input))

}

fun String.nextMarker(start: Int = 0): String? {
    return """\(\d+x\d+\)""".toRegex().find(this, start)?.value
}

