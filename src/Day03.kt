fun main() {


    fun part1(input: Input): Int {
        val cols: MutableMap<Int, MutableList<Char>> = input.toColumnMap()

        val gammaRate = cols.map { (_, col) ->
            if (col.count { it == ZERO } > col.count { it == ONE }) {
                '0'
            } else {
                '1'
            }
        }.joinToString("").toInt(2)
        
        val epsilonRate = cols.map { (_, col) ->
            if (col.count { it == ZERO } < col.count { it == ONE }) {
                '0'
            } else {
                '1'
            }
        }.joinToString("").toInt(2)

        return gammaRate * epsilonRate
    }


    fun part2(input: Input): Int {
        val cols: MutableMap<Int, MutableList<Char>> = input.toColumnMap()
        
        return oxygen(input, 0).toInt(2) * co2(input, 0).toInt(2)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun Input.toColumnMap(): MutableMap<Int, MutableList<Char>> {
    val cols: MutableMap<Int, MutableList<Char>> = mutableMapOf()
    forEach { line ->
        line.mapIndexed { index, c: Char ->
            val col = cols[index] ?: mutableListOf()
            col.add(c)
            cols[index] = col
        }
    }
    return cols
}

fun oxygen(input: Input, index: Int): String {
    if (input.size == 1) 
        return input.first()

    val cols: MutableMap<Int, MutableList<Char>> = input.toColumnMap()

    val foo = cols.map { (_, col) ->
        if (col.count { it == ZERO } > col.count { it == ONE }) {
            '0'
        } else {
            '1'
        }
    }[index]

    return oxygen(
        input = input.filter { line ->
            line[index] == foo
        }, index = index +1)
}

fun co2(input: Input, index: Int): String {
    if (input.size == 1) 
        return input.first()

    val cols: MutableMap<Int, MutableList<Char>> = input.toColumnMap()

    val foo = cols.map { (_, col) ->
        if (col.count { it == ZERO } <= col.count { it == ONE }) {
            '0'
        } else {
            '1'
        }
    }[index]


    return co2(
        input = input.filter { line ->
            line[index] == foo
        }, index = index +1)
}

const val ZERO = '0'
const val ONE = '1'