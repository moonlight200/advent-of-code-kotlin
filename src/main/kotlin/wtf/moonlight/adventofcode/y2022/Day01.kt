package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.loadFileAsSequence
import wtf.moonlight.adventofcode.util.readFile
import kotlin.math.max

fun main() {
    fun part1() {
        val input = readFile("input.txt", 1)
        var maxCalories = Int.MIN_VALUE
        var sumCalories = 0

        for (line in input) {
            if (line.isBlank()) {
                maxCalories = max(maxCalories, sumCalories)
                sumCalories = 0
            } else {
                sumCalories += line.toInt()
            }
        }
        maxCalories = max(maxCalories, sumCalories)

        println(maxCalories)
    }

    fun part2() {
        val (calories, last) = loadFileAsSequence("input.txt", 1)
            .fold(Pair<List<Int>, Int>(emptyList(), 0)) { (list, accumulator), line ->
                if (line.isBlank()) list + accumulator to 0
                else list to accumulator + line.toInt()
            }

        val top3Sum = (calories + last).sortedDescending().take(3).sum()

        println(top3Sum)
    }

    part1()
    println("--------")
    part2()
}
