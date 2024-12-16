fun main() {
    fun rule(number: String): Pair<String, String?> {
        return if (number == "0") {
            Pair("1", null)
        } else if (number.length % 2 == 0) {
            Pair(number.substring(0, number.length / 2), number.substring(number.length / 2).toLong().toString())
        } else {
            Pair((number.toLong() * 2024).toString(), null)
        }
    }

    fun part1(input: List<String>): Int {
        val mutableList = input.toMutableList()

        repeat(25) {
            val size = mutableList.size

            for (i in 0 until size) {
                val (rule1, rule2) = rule(mutableList[i])
                mutableList[i] = rule1
                rule2?.let {
                    mutableList.add(it)
                }
            }
        }

        return mutableList.size
    }

    val memo = HashMap<Long, HashMap<Int, Long>>()
    fun part2(input: Long, leftBlink: Int): Long {
        if (leftBlink == 0) {
            return 0
        }
        if (memo.containsKey(input)) {
            if (memo[input]!!.containsKey(leftBlink)) {
                return memo[input]!![leftBlink]!!
            }
        } else {
            memo[input] = HashMap()
        }

        return if (input == 0L) {
            val stoneCount = part2(1, leftBlink - 1)
            memo[input]!![leftBlink] = stoneCount
            stoneCount
        } else if (input.toString().length % 2 == 0) {
            val stringStone = input.toString()
            val stoneCount = 1 + part2(stringStone.substring(0, stringStone.length / 2).toLong(), leftBlink - 1) +
                    part2(stringStone.substring(stringStone.length / 2).toLong(), leftBlink - 1)
            memo[input]!![leftBlink] = stoneCount
            stoneCount
        } else {
            val stoneCount = part2(input * 2024, leftBlink - 1)
            memo[input]!![leftBlink] = stoneCount
            stoneCount
        }
    }


    val input = readInput("Day11")

    println(part1(input.first().split(" ")))
    println(input.first().split(" ")
        .map { it.toLong() }
        .sumOf {
            1 + part2(it, 75)
        }
    )
}
