package `2016`

import readInput


fun main() {
    fun part1(input: List<String>): Int {

        val foo = input.map(String::toRoom)
            .sumOf { if (it.isValid()) it.id else 0 }
        return foo
    }

    fun part2(input: List<String>): Int {
        input.map { it.toRoom() }.forEach { 
            if(it.realName().contains("northpole")) println("${it.realName()} $it.id")
        }
            
        return 0
    }

    val testInput = readInput("2016/day04_test_input")
    check(part1(testInput) == 1514)
//    check(part2(testInput) == 0)

    val input = readInput("2016/day04_input")
    println(part1(input))
    println(part2(input))
}

fun String.toRoom(): Room {
    val s = this.split("[")
    val checksum = s.last().dropLast(1)
    val s2 = s.first().split("-")
    val id = s2.last().toInt()
    val name = s2.dropLast(1).joinToString(" ")
    return Room(checksum = checksum, id = id, name = name)
}


data class Room(val name: String, val id: Int, val checksum: String) {
    fun isValid(): Boolean {
        val mostCommonChars = ('a'..'z')
            .map { c -> c to name.count { it == c } }.sortedByDescending { it.second }
            .let {
                it.take(5).filter {
                    it.second > 0
                }
            }.map { it.first }
        return mostCommonChars.containsAll(checksum.toList())
    }
    
    fun realName(): String {
        val rmap = ('a'..'z').mapIndexed {index, c -> index + 1 to c}
        val rotation = id % 26
        
        return name.map{ c->
            if (c != ' ') {
                val rotIdx = (rmap.first { it.second == c }.first + rotation) % 26
                if (rotIdx != 0) rmap.first { it.first == rotIdx }.second else c
            } else c
        }.joinToString ("")
    }
}