import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val splitInput = input.map { it.split("   ") }
        val part1 = splitInput.map { it.first().toInt() }.sorted()
        val part2 = splitInput.map { it.last().toInt() }.sorted()

        return part1.zip(part2).sumOf { (p1, p2) -> abs(p1 - p2) }
    }

    fun part2(input: List<String>): Int {
        val splitInput = input.map { it.split("   ") }
        val part1 = splitInput.map { it.first() }.groupingBy { it }.eachCount()
        val part2 = splitInput.map { it.last() }.groupingBy { it }.eachCount()
        return part1.entries.sumOf { (k, v) -> part2[k]?.let { v * k.toInt() * it } ?: 0 }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")

    println(part1(input))
    println(part2(input))
}
