fun main() {
    fun parse(m: String): Int {
        val first = m.substringAfter("(").substringBefore(",").toInt()
        val second = m.substringAfter(",").substringBefore(")").toInt()
        return first * second
    }

    fun part1(lines: List<String>): Int {
        val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        return regex.findAll(lines.joinToString(" ")).map { it.value }.toList().sumOf { parse(it) }
    }

    fun part2(lines: List<String>): Int {
        val l = lines.joinToString(" ")
        val mulInstructionRegex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        val doRegex = Regex("""do\(\)""")
        val doNot = Regex("""don't\(\)""").findAll(l).map { it.range.last }.toList()

        val ignoreRange = doNot.map {
            val startDo = (doRegex.find(l.substring(it))?.range?.endInclusive  ?: l.length) + it
            IntRange(it, startDo)
        }

        return mulInstructionRegex.findAll(l)
            .filter {
                ignoreRange.none { range -> it.range.first in range }
            }
            .map { it.value }.toList().sumOf { parse(it) }
    }

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}
