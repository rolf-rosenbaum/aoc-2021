package `2016`


val magicNumber = 1352
//val goal = Point(31, 39)

fun part1(): Int {


    val path = findPath()


    return path
}

fun part2(): Int {
    val maze: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    var distance = 0
    maze[1 to 1] = 0

    do  {
        maze.filterValues { it == distance }.forEach { (x, y), _ ->
            neighbors(x, y).forEach { (x1, y1) ->
                if (maze[x1 to y1] == null) {
                    maze[x1 to y1] = distance + 1
                }
            }
        }
//        prettyPrint(maze)
        distance++
    } while (distance < 50)

    return maze.size
}

fun main() {


    println(part1())
    println(part2())
}


fun isWall(x: Int, y: Int): Boolean {
    val num = (x * x + 3 * x +  2 * x * y+y+y * y+magicNumber).toString(2)
    return num.count { it == '1' } % 2 == 1
}

fun isOutside(x: Int, y: Int): Boolean {
    return x < 0 || y < 0
}

fun neighbors(x: Int, y: Int): List<Pair<Int, Int>> {
    return listOf(
        x + 1 to y,
        x to y + 1,
        x - 1 to y,
        x to y - 1,
    ).filterNot { isWall(it.first, it.second) || isOutside(it.first, it.second) }
}

fun prettyPrint(maze: Map<Pair<Int, Int>, Int>) {
    (0 until 45).forEach { y ->
        (0 until 45).forEach { x ->
            if (isWall(x, y)) print("#") else if (maze[x to y] == null) print(" ") else print("0")
        }
        println()
    }
    println()
}

fun findPath(): Int {

    val maze: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    var distance = 0
    maze[1 to 1] = 0

    while (maze[31 to 39] == null) {
        maze.filterValues { it == distance }.forEach { (x, y), _ ->
            neighbors(x, y).forEach { (x1, y1) ->
                if (maze[x1 to y1] == null) {
                    maze[x1 to y1] = distance + 1
                }
            }
        }
//        prettyPrint(maze)
        distance++
    }
    return maze[31 to 39] ?: 0
}