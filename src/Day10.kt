typealias Point10 = Pair<Int, Int>

var mapX = 0
var mapY = 0
var map: List<List<Int>> = listOf()

fun main() {

    fun isInMap(p: Point10): Boolean {
        return p.first in 0 until mapX && p.second in 0 until mapY
    }
    var reachPoints = mutableListOf<Point10>()
    fun hiking(p: Point10, prevHeight: Int): Int {
        if (!isInMap(p)) {
            return 0
        }
        if (map[p.second][p.first] - prevHeight != 1) {
            return 0
        }
        if (map[p.second][p.first] == 9) {
            reachPoints.add(p)
            return 1
        }
        val thisHeight = map[p.second][p.first]
        return hiking(p.copy(first = p.first + 1), thisHeight) +
                hiking(p.copy(first = p.first - 1), thisHeight) +
                hiking(p.copy(second = p.second + 1), thisHeight) +
                hiking(p.copy(second = p.second - 1), thisHeight)
    }

    fun part1(): Int {
        var count = 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 0) {
                    hiking(Point10(x, y), -1)
                    count += reachPoints.distinct().count()
                    reachPoints.clear()
                }
            }
        }
        return count
    }

    fun part2(): Int {
        var count = 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 0) {
                    count += hiking(Point10(x, y), -1)
                }
            }
        }
        return count
    }

    val input = readInput("Day10").map{
        it.map { c -> c.code - 48 }
    }

    mapX = input[0].size
    mapY = input.size
    map = input

    println(part1())
    println(part2())
}