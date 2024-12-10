fun main() {
    fun part1(input: String): Long {
        val blocks = mutableListOf<Int?>()

        input.map { it.code - 48 }.plus(0).chunked(2).forEachIndexed { i, diskMap ->
            repeat(diskMap[0]) {
                blocks.add(i)
            }
            repeat(diskMap[1]) {
                blocks.add(null)
            }
        }

        var begin = 0
        var end = blocks.lastIndex
        while (begin < end) {
            if (blocks[begin] == null) {
                blocks[begin] = blocks[end]
                end -= 1
                begin += 1

                while (begin < end && blocks[end] == null) {
                    end -= 1
                }
            } else {
                begin += 1
            }
        }
        return (0..end).map { b ->
            b * (blocks[b] ?: 0)
        }.sumOf { it.toLong() }
    }

    data class File(
        val size: Int,
        val id: Int?,
    )

    fun part2(input: String): Long {
        val blocks = mutableListOf<File>()

        input.map { it.code - 48 }.plus(0).chunked(2).forEachIndexed { i, diskMap ->
            blocks.add(File(diskMap[0], i))
            blocks.add(File(diskMap[1], null))
        }

        var end = blocks.lastIndex
        while (end > 0) {
            if (blocks[end].id == null) {
                end -= 1
            } else {
                var begin = 0
                while (begin < end) {
                    if (blocks[begin].id == null && blocks[begin].size >= blocks[end].size) {
                        break
                    }
                    begin += 1
                }
                if (begin < end) {
                    if (blocks[begin].size == blocks[end].size) {
                        val file = blocks[end]
                        blocks[end] = blocks[begin]
                        blocks[begin] = file
                    } else {
                        val file = blocks[end]
                        val space = blocks[begin].size
                        blocks[end] = File(file.size, null)
                        blocks[begin] = file
                        blocks.add(begin + 1, File(space - file.size, null))
                    }
                }
                end -=1
            }
        }

        return blocks.flatMap { (size, id) ->
            List(size) { _ -> id }
        }.mapIndexed { i, number ->
            i * (number ?: 0)
        }.sumOf { it.toLong() }
    }

    val input = readInput("Day09")
        .first()

    println(part1(input))
    println(part2(input))
}

