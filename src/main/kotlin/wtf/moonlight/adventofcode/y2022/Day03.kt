package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile


fun main() {
    fun Char.priority(): Int =
        when (this) {
            in 'a'..'z' -> this.code - 'a'.code + 1
            in 'A'..'Z' -> this.code - 'A'.code + 27
            else -> throw IllegalStateException("Unknown common type: $this")
        }

    fun part1() {
        val input = readFile("input.txt", 3)
        val prioritySum = input.sumOf {
            val compartment1 = it.substring(0, it.length / 2).toSet()
            val compartment2 = it.substring(it.length / 2).toSet()
            val commonTypes = compartment1.intersect(compartment2)
            if (commonTypes.size != 1)
                throw IllegalStateException("Found more or less than 1 item type in both compartments: $it")
            commonTypes.first().priority()
        }
        println(prioritySum)
    }

    fun part2() {
        val input = readFile("input.txt", 3)
        val prioritySum = input.chunked(3) {
            val badge = it.map { contents -> contents.toSet() }
                .reduce { a, b -> a.intersect(b) }
            if (badge.size != 1)
                throw IllegalStateException("Found more ore less than 1 group badge candidates: $it")
            badge.first().priority()
        }.sum()
        println(prioritySum)
    }

    part1()
    println("--------")
    part2()
}
