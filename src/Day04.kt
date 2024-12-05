enum class Direction(val x: Int, val y: Int) {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1)
}

fun main() {
    val xMas = "XMAS"
    fun isXmas(lines: List<String>, x: Int, y: Int, direction: Direction): Boolean {
        for (i in xMas.indices) {
            val moveX = x + i * direction.x
            val moveY = y + i * direction.y
            if (moveX < 0 || moveX >= lines[0].length || moveY < 0 || moveY >= lines.size) {
                return false
            }
            if (lines[moveY][moveX] != xMas[i]) {
                return false
            }
        }
        return true
    }

    fun isCrossMax(lines: List<String>, x: Int, y: Int): Boolean {
        var mCount = 0
        var sCount = 0

        listOf(Direction.UP_LEFT, Direction.DOWN_RIGHT).forEach {
            val moveX = x + it.x
            val moveY = y + it.y
            if (moveX < 0 || moveX >= lines[0].length || moveY < 0 || moveY >= lines.size) {
                return false
            }
            if (lines[moveY][moveX] == 'M') {
                mCount += 1
            }
            if (lines[moveY][moveX] == 'S') {
                sCount += 1
            }
        }
        if (mCount != 1 || sCount != 1) {
            return false
        }
        listOf(Direction.UP_RIGHT, Direction.DOWN_LEFT).forEach {
            val moveX = x + it.x
            val moveY = y + it.y
            if (moveX < 0 || moveX >= lines[0].length || moveY < 0 || moveY >= lines.size) {
                return false
            }
            if (lines[moveY][moveX] == 'M') {
                mCount += 1
            }
            if (lines[moveY][moveX] == 'S') {
                sCount += 1
            }
        }
        return mCount == 2 && sCount == 2
    }

    fun part1(lines: List<String>): Int {
        val rowSize = lines[0].length
        val colSize = lines.size

        var count = 0
        for (y in 0 until colSize) {
            for (x in 0 until rowSize) {
                for (direction in Direction.entries) {
                    if (isXmas(lines, x, y, direction)) {
                        count += 1
                    }
                }
            }
        }
        return count
    }

    fun part2(lines: List<String>): Int {
        val rowSize = lines[0].length
        val colSize = lines.size
        var count = 0

        for (y in 0 until colSize) {
            for (x in 0 until rowSize) {
                if (lines[y][x] == 'A' && isCrossMax(lines, x, y)) {
                    count += 1
                }
            }
        }
        return count
    }

    val input = readInput("Day04")

    println(part1(input))
    println(part2(input))
}
