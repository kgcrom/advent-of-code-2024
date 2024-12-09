import kotlin.math.abs

typealias Point08 = Pair<Int, Int>

fun main() {

    var mapX = 0
    var mppY = 0

    fun isInMap(point: Point08, mapX: Int, maxY: Int): Boolean =
        point.first in 0 until mapX && point.second in 0 until maxY

    fun findAntiNode(base: Point08, other: Point08): Pair<Point08, Point08> {
        val diffx = abs(other.first - base.first)
        val diffy = abs(other.second - base.second)

        return if (base.first < other.first) {
            // base: 왼쪽 위, other: 오른쪽 아래
            if (base.second < other.second) {
                Point08(base.first - diffx, base.second - diffy) to
                        Point08(other.first + diffx, other.second + diffy)
            } else {  // base: 왼쪽 아래, other: 오른쪽 위
                Point08(base.first - diffx, base.second + diffy) to
                        Point08(other.first + diffx, other.second - diffy)
            }
        } else {
            // base: 오른쪽 위, other: 왼쪽 아래
            if (base.second < other.second) {
                Point08(base.first + diffx, base.second - diffy) to
                        Point08(other.first - diffx, other.second + diffy)
            } else {  // base: 오른쪽 아래, other: 왼쪽 위
                Point08(base.first + diffx, base.second + diffy) to
                        Point08(other.first - diffx, other.second - diffy)
            }
        }
    }

    fun extension(point: Point08, directX: Int, directY: Int, mapX: Int, mapY: Int): List<Point08> {
        val result = mutableListOf<Point08>()
        result.add(Point08(point.first, point.second))
        var x = point.first + directX
        var y = point.second + directY

        while (isInMap(Point08(x, y), mapX, mapY)) {
            result.add(Point08(x, y))
            x += directX
            y += directY
        }
        return result
    }

    fun findAntiNodePart2(base: Point08, other: Point08, mapX: Int, mapY: Int):List<Point08> {
        val diffx = abs(other.first - base.first)
        val diffy = abs(other.second - base.second)

        return if (base.first < other.first) {
            // base: 왼쪽 위, other: 오른쪽 아래
            if (base.second < other.second) {
                extension(base, -diffx, -diffy, mapX, mapY) +
                        extension(other, diffx, diffy, mapX, mapY)
            } else {  // base: 왼쪽 아래, other: 오른쪽 위
                extension(base, -diffx, diffy, mapX, mapY) +
                        extension(other, diffx, -diffy, mapX, mapY)
            }
        } else {
            // base: 오른쪽 위, other: 왼쪽 아래
            if (base.second < other.second) {
                extension(base, diffx, -diffy, mapX, mapY) +
                        extension(other, -diffx, diffy, mapX, mapY)
            } else {  // base: 오른쪽 아래, other: 왼쪽 위
                Point08(base.first + diffx, base.second + diffy) to
                        Point08(other.first - diffx, other.second - diffy)
                extension(base, diffx, diffy, mapX, mapY) +
                        extension(other, -diffx, -diffy, mapX, mapY)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val antennaMap = mutableMapOf<Char, List<Point08>>()
        for (y in input.indices) {
            for (x in input[0].indices) {
                val key = input[y][x]
                if (key != '.') {
                    antennaMap[key] = antennaMap.getOrDefault(key, listOf()) + Pair(x, y)
                }
            }
        }

        var count = 0
        val allAntiNodes = mutableSetOf<Point08>()
        for (m in antennaMap) {
            val savedAntiNodes = mutableSetOf<Point08>()

            for (i in m.value.indices) {
                for (j in i + 1 until m.value.size) {
                    val antiNodes = findAntiNode(m.value[i], m.value[j])

                    if (!allAntiNodes.contains(antiNodes.first)) {
                        savedAntiNodes.add(antiNodes.first)
                    }
                    if (!allAntiNodes.contains(antiNodes.second)) {
                        savedAntiNodes.add(antiNodes.second)
                    }
                }
            }

            savedAntiNodes.removeAll(m.value.toSet())
            val antiNodes = savedAntiNodes.toList().filter { isInMap(it, input[0].length, input.size) }
            allAntiNodes.addAll(antiNodes)
            count += antiNodes.size
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val antennaMap = mutableMapOf<Char, List<Point08>>()
        for (y in input.indices) {
            for (x in input[0].indices) {
                val key = input[y][x]
                if (key != '.') {
                    antennaMap[key] = antennaMap.getOrDefault(key, listOf()) + Pair(x, y)
                }
            }
        }

        var count = 0
        val allAntiNodes = mutableSetOf<Point08>()
        for (m in antennaMap) {
            val savedAntiNodes = mutableSetOf<Point08>()

            for (i in m.value.indices) {
                for (j in i + 1 until m.value.size) {
                    val antiNodes = findAntiNodePart2(m.value[i], m.value[j], input[0].length, input.size)

                    savedAntiNodes.addAll(
                        antiNodes.filter { !allAntiNodes.contains(it) }
                    )
                }
            }
            val antiNodes = savedAntiNodes.toList().filter { isInMap(it, input[0].length, input.size) }
            allAntiNodes.addAll(antiNodes)
            count += antiNodes.size
        }
        return count
    }

    val input = readInput("Day08")

    println(part1(input))
    println(part2(input))
}