fun main() {

    fun isOrdered(page: List<String>, rules: Map<String, List<String>>): Boolean {
        return page.windowed(2).all {
            rules.any { rule ->
                rule.key == it[0] && rule.value.contains(it[1])
            }
        }
    }

    fun part1(input: List<String>): Int {
        val rules = input.dropLastWhile { it.contains(",") }.dropLast(1)
            .map {
                val (first, second) = it.split("|")
                first to second
            }.groupBy { it.first }
            .mapValues { it -> it.value.map { it.second } }

        val pages = input.dropWhile { it.isNotBlank() }.drop(1)
            .map { it.split(",") }
        return pages.filter { page -> isOrdered(page, rules) }
            .sumOf { p ->
                p[p.size / 2].toInt()
            }
    }

    fun beSafety(unSafety: MutableList<String>, rules: Map<String, List<String>>): List<String> {
        var isSafety = false
        while (!isSafety) {
            isSafety = true
            rules.forEach { (key, values) ->
                for (k in 0 until unSafety.size) {
                    for (l in 0 until unSafety.size) {
                        if (k == l) {
                            continue
                        }
                        if (k > l && key == unSafety[k] && values.contains(unSafety[l])) {
                            val temp = unSafety[k]
                            unSafety[k] = unSafety[l]
                            unSafety[l] = temp
                            isSafety = false
                        }
                    }
                }
            }
        }

        return unSafety
    }

    fun part2(input: List<String>): Int {
        val rules = input.dropLastWhile { it.contains(",") }.dropLast(1)
            .map {
                val (first, second) = it.split("|")
                first to second
            }
            .groupBy { it.first }
            .mapValues { it -> it.value.map { it.second } }
        val pages = input.dropWhile { it.isNotBlank() }.drop(1)
            .map { it.split(",") }

        val unSafetyPages = pages.filter { page -> !isOrdered(page, rules) }

        return unSafetyPages.sumOf { unSafetyPage ->
            val safetyPage = beSafety(unSafetyPage.toMutableList(), rules)
            safetyPage[safetyPage.size / 2].toInt()
        }
    }

    val input = readInput("Day05")

    println(part1(input))
    println(part2(input))
}