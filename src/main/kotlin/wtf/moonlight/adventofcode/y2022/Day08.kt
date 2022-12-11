package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile
import kotlin.math.max


fun main() {
    fun List<String>.parseTreeHeights() =
        map { line ->
            line.map {
                it.digitToInt()
            }.toIntArray()
        }.toTypedArray()

    fun part1() {
        val input = readFile("input.txt", 8)
        val treeHeights = input.parseTreeHeights()
        val areaHeight = treeHeights.size
        val areaWidth = treeHeights.first().size

        val visible = Array(areaHeight) {
            BooleanArray(areaWidth) { false }
        }

        // Left
        for (y in 0 until areaHeight) {
            var heighest = -1
            for (x in 0 until areaWidth) {
                if (treeHeights[y][x] <= heighest)
                    continue

                visible[y][x] = true
                heighest = treeHeights[y][x]

                if (heighest == 9)
                    break
            }
        }

        // Right
        for (y in 0 until areaHeight) {
            var heighest = -1
            for (x in areaWidth - 1 downTo 0) {
                if (treeHeights[y][x] <= heighest)
                    continue

                visible[y][x] = true
                heighest = treeHeights[y][x]

                if (heighest == 9)
                    break
            }
        }

        // Top
        for (x in 0 until areaWidth) {
            var heighest = -1
            for (y in 0 until areaHeight) {
                if (treeHeights[y][x] <= heighest)
                    continue

                visible[y][x] = true
                heighest = treeHeights[y][x]

                if (heighest == 9)
                    break
            }
        }

        // Bottom
        for (x in 0 until areaWidth) {
            var heighest = -1
            for (y in areaHeight - 1 downTo 0) {
                if (treeHeights[y][x] <= heighest)
                    continue

                visible[y][x] = true
                heighest = treeHeights[y][x]

                if (heighest == 9)
                    break
            }
        }

        println(visible.sumOf { row -> row.count { it } })
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
        Pair(first + other.first, second + other.second)

    operator fun Array<IntArray>.get(point: Pair<Int, Int>): Int =
        this[point.second][point.first]

    fun Array<IntArray>.directionalScenicView(start: Pair<Int, Int>, direction: Pair<Int, Int>): Int {
        var current = start + direction
        var view = 0

        while (current.first in first().indices && current.second in indices) {
            if (this[start] > this[current]) {
                view++
            } else {
                view++
                break
            }

            current += direction
        }

        return view
    }

    fun part2() {
        val input = readFile("input.txt", 8)
        val treeHeights = input.parseTreeHeights()
        val areaHeight = treeHeights.size
        val areaWidth = treeHeights.first().size

        var highestScenicScore = Int.MIN_VALUE

        for (y in 0 until areaHeight) {
            for (x in 0 until areaWidth) {
                val scenicScore =
                    treeHeights.directionalScenicView(x to y, 0 to 1) *
                            treeHeights.directionalScenicView(x to y, 0 to -1) *
                            treeHeights.directionalScenicView(x to y, 1 to 0) *
                            treeHeights.directionalScenicView(x to y, -1 to 0)

                highestScenicScore = max(scenicScore, highestScenicScore)
            }
        }

        println(highestScenicScore)
    }

    part1()
    println("--------")
    part2()
}
