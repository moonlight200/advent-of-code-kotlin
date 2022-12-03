package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile


fun main() {
    fun part1() {
        // A = Rock, B = Paper, C = Scissors
        // X = Rock, Y = Paper, Z = Scissors
        val resultMap = mapOf(
            "A X" to 1 + 3,
            "A Y" to 2 + 6,
            "A Z" to 3 + 0,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 1 + 6,
            "C Y" to 2 + 0,
            "C Z" to 3 + 3,
        )

        val input = readFile("input.txt", 2)
        println(input.sumOf {
            resultMap[it] ?: throw IllegalArgumentException("Game not found: $it")
        })
    }

    fun part2() {
        // A = Rock, B = Paper, C = Scissors
        // X = Loose, Y = Draw, Z = Win
        val resultMap = mapOf(
            "A X" to 3 + 0, // S
            "A Y" to 1 + 3, // R
            "A Z" to 2 + 6, // P
            "B X" to 1 + 0, // R
            "B Y" to 2 + 3, // P
            "B Z" to 3 + 6, // S
            "C X" to 2 + 0, // P
            "C Y" to 3 + 3, // S
            "C Z" to 1 + 6, // R
        )

        val input = readFile("input.txt", 2)
        println(input.sumOf {
            resultMap[it] ?: throw IllegalArgumentException("Game not found: $it")
        })
    }

    part1()
    println("--------")
    part2()
}
