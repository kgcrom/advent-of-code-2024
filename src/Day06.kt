class Point06(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point06) return false
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        return 31 * x + y
    }

    fun isInMap(maxX: Int, maxY: Int): Boolean {
        return x in 0 until maxX && y in 0 until maxY
    }

    fun move(moveX: Int, moveY: Int) = Point06(moveX, moveY)
}

enum class Direction06(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    ;

    fun turnRight(): Direction06 {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }
}

fun main() {
    fun findGuard(input: List<String>): Point06 {
        for (y in input.indices) {
            val x = input[y].indexOf('^')
            if (x != -1) {
                return Point06(x, y)
            }
        }
        throw IllegalArgumentException("Guard not found")
    }

    fun footprint(input: List<String>, guard: Point06): Set<Point06> {
        val set = mutableSetOf<Point06>()
        var moveGuard = guard
        var direction = Direction06.UP
        while (moveGuard.isInMap(input[0].length, input.size)) {
            set.add(moveGuard)
            val next = Point06(moveGuard.x + direction.dx, moveGuard.y + direction.dy)
            if (!next.isInMap(input[0].length, input.size)) {
                break
            }
            if (input[next.y][next.x] == '#') {
                direction = direction.turnRight()
                continue
            }
            moveGuard = moveGuard.move(moveGuard.x + direction.dx, moveGuard.y + direction.dy)
        }
        return set
    }

    fun part1(input: List<String>): Int {
        val set = footprint(input, findGuard(input))
        return set.size
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toCharArray().toMutableList() }
        val set = mutableSetOf<Pair<Point06, Direction06>>()
        val guard = findGuard(input)
        var count = 0

        val footPrint = footprint(input, guard)

        var originalGrid: Char

        for (point in footPrint) {
            var moveGuard = Point06(guard.x, guard.y)
            var direction = Direction06.UP
            originalGrid = grid[point.y][point.x]
            grid[point.y][point.x] = '#'
            set.clear()
            while (moveGuard.isInMap(input[0].length, input.size)) {
                if (!set.add(Pair(moveGuard, direction))) {
                    count += 1
                    break
                }
                val next = Point06(moveGuard.x + direction.dx, moveGuard.y + direction.dy)
                if (!next.isInMap(input[0].length, input.size)) {
                    break
                }
                if (grid[next.y][next.x] == '#') {
                    direction = direction.turnRight()
                    continue
                }
                moveGuard = moveGuard.move(moveGuard.x + direction.dx, moveGuard.y + direction.dy)
            }
            grid[point.y][point.x] = originalGrid
        }
        return count
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}