fun main() {
    fun isSafe(report: List<Int>): Boolean {
        val direction = if (report[0] > report[1]) {
            1
        } else if (report[0] < report[1]) {
            -1
        } else {
            0
        }
        if (direction == 0) {
            return false
        }
        return report.reduce { acc, i ->
            if (acc == -1) {
                return false
            }
            val diff = (acc - i) * direction

            if (diff in 1..3) {
                i
            } else {
                -1
            }
        } != -1
    }

    fun part1(input: List<String>): Int {
        val reports = input.map { it.split(" ").map(String::toInt) }

        return reports.count { isSafe(it) }
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { it.split(" ").map(String::toInt) }

        return reports.count { report ->
            for (i in report.indices) {
                val newReport = report.toMutableList()
                newReport.removeAt(i)

                if (isSafe(newReport)) {
                    return@count true
                }
            }
            false
        }
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")

    println(part1(input))
    println(part2(input))
}
