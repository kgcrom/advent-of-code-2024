fun main() {
    fun isValidEquation(l: Pair<Long, List<Int>>, total: Long, idx: Int, isPart2Rule: Boolean = false): Boolean {
        if (l.first == total && idx == l.second.lastIndex) {
            return true
        }

        if (total > l.first || total < 0) {
            return false
        }
        if (idx >= l.second.lastIndex) {
            return false
        }

        val addTotal = total + l.second[idx + 1]
        val multipleTotal = total * l.second[idx + 1]
        val concatTotal = if (isPart2Rule) {
            (total.toString() + l.second[idx + 1].toString()).toLong()
        } else {
            -1
        }

        return isValidEquation(l, addTotal, idx + 1, isPart2Rule)
                || isValidEquation(l, multipleTotal, idx + 1, isPart2Rule)
                ||
                if (isPart2Rule) {
                    isValidEquation(l, concatTotal, idx + 1, true)
                } else {
                    false
                }
    }

    fun part1(input: List<Pair<Long, List<Int>>>): Long {
        return input.sumOf { l ->
            val valid = isValidEquation(l, l.second.first().toLong(), 0)
            if (valid) {
                l.first
            } else {
                0
            }
        }
    }

    fun part2(input: List<Pair<Long, List<Int>>>): Long {
        return input.sumOf { l ->
            val valid = isValidEquation(l, l.second.first().toLong(), 0, true)
            if (valid) {
                l.first
            } else {
                0
            }
        }
    }

    val input = readInput("Day07")
        .map { it ->
            val (total, numbers) = it.split(":")

            total.toLong() to numbers.drop(1).split(" ").map { it.toInt() }
        }

    println(part1(input))
    println(part2(input))
}
