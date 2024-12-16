typealias Point13 = Pair<Long, Long>
typealias MachineInfo = Triple<Button, Button, Button>

data class Button(
    val p: Point13,
    val cost: Int,
) {

    operator fun plus(other: Button): Button {
        return Button(Point13(p.first + other.p.first, p.second + other.p.second), cost + other.cost)
    }

    operator fun minus(other: Button): Button {
        return Button(Point13(p.first - other.p.first, p.second - other.p.second), cost - other.cost)
    }

    operator fun compareTo(other: Button): Int {
        return maxOf(p.first.compareTo(other.p.first), p.second.compareTo(other.p.second))
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Button -> p.first.compareTo(other.p.first) == 0 && p.second.compareTo(other.p.second) == 0
            else -> false
        }
    }
}

fun main() {
    val buttonRegex = "Button [AB]: X\\+(\\d+), Y\\+(\\d+)".toRegex()
    val prizeRegex = "Prize: X=(\\d+), Y=(\\d+)".toRegex()

    fun extractMachineInfo(input: List<String>, part2: Boolean = false) = input.filter { it.isNotEmpty() }
        .chunked(3)
        .map { it ->
            val (aX, aY) = buttonRegex.find(it[0])?.destructured?.let {
                it.component1().toLong() to it.component2().toLong()
            } ?: throw IllegalArgumentException("Invalid input")

            val (bX, bY) = buttonRegex.find(it[1])?.destructured?.let {
                it.component1().toLong() to it.component2().toLong()
            } ?: throw IllegalArgumentException("Invalid input")

            val (pX, pY) = prizeRegex.find(it[2])?.destructured?.let {
                if (part2) {
                    it.component1().toLong() + 10000000000000 to it.component2().toLong() + 10000000000000
                } else {
                    it.component1().toLong() to it.component2().toLong()
                }
            } ?: throw IllegalArgumentException("Invalid input")

            MachineInfo(Button(Point13(aX, aY), 3), Button(Point13(bX, bY), 1), Button(Point13(pX, pY), 0))
        }

    fun part1(input: List<String>): Int {
        val machineInfoList = extractMachineInfo(input)

        return machineInfoList.sumOf {
            val aButton = it.first
            val bButton = it.second
            val prize = it.third
            var aCount = 0

            var clawLocation = Button(Point13(0, 0), 0)
            while (clawLocation <= prize - aButton) {
                clawLocation += aButton

                aCount += 1
            }

            (aCount downTo 0).forEach {
                while (clawLocation <= (prize - bButton)) {
                    clawLocation += bButton
                }

                if (clawLocation == prize) return@forEach

                clawLocation -= aButton
            }

            if (clawLocation == prize) clawLocation.cost else 0
        }
    }

    fun part2(input: List<String>): Long {
        val machineInfoList = extractMachineInfo(input, true)

        return machineInfoList.sumOf {
            val aButton = it.first
            val bButton = it.second
            val prize = it.third

            val nb =
                (aButton.p.first * prize.p.second - aButton.p.second * prize.p.first) / (aButton.p.first * bButton.p.second - aButton.p.second * bButton.p.first)
            val na = (prize.p.second - bButton.p.second * nb) / aButton.p.second

            if (
                aButton.p.second * na + bButton.p.second * nb == prize.p.second &&
                aButton.p.first * na + bButton.p.first * nb == prize.p.first
            ) {
                (3 * na) + nb
            } else {
                0
            }
        }
    }

    val input = readInput("Day13")

    println(part1(input))
    println(part2(input))
}
