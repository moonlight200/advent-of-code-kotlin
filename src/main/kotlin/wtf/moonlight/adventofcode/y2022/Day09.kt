package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile
import kotlin.math.abs
import kotlin.math.sqrt


fun main() {
    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
        first + other.first to second + other.second

    fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        val deltaX = head.first - tail.first
        val deltaY = head.second - tail.second

        val totalDistance = sqrt((deltaX * deltaX + deltaY * deltaY).toDouble())
        if (totalDistance < 2.0) return tail

        // If one or both are 0
        val movement = if (deltaX * deltaY == 0) {
            (if (abs(deltaX) > 1) deltaX.coerceIn(-1, 1) else 0) to (if (abs(deltaY) > 1) deltaY.coerceIn(-1, 1) else 0)
        } else {
            deltaX.coerceIn(-1, 1) to deltaY.coerceIn(-1, 1)
        }

        return tail + movement
    }

    fun part1() {
        val input = readFile("input.txt", 9)
        var head = 0 to 0
        var tail = 0 to 0
        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(tail)

        input.forEach { line ->
            val headDelta = when (line.first()) {
                'U' -> 0 to 1
                'D' -> 0 to -1
                'R' -> 1 to 0
                'L' -> -1 to 0
                else -> throw IllegalArgumentException("Unknown movement: $line")
            }

            for (i in 0 until line.substring(2).toInt()) {
                head += headDelta
                tail = moveTail(head, tail)
                tailPositions.add(tail)
            }
        }

        println(tailPositions.size)
    }

    fun part2() {
        val input = readFile("input.txt", 9)
        val rope = Array(10) { 0 to 0 }
        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(rope.last())

        input.forEach { line ->
            val headDelta = when (line.first()) {
                'U' -> 0 to 1
                'D' -> 0 to -1
                'R' -> 1 to 0
                'L' -> -1 to 0
                else -> throw IllegalArgumentException("Unknown movement: $line")
            }

            for (i in 0 until line.substring(2).toInt()) {
                rope[0] += headDelta

                for (segment in 1 until rope.size) {
                    rope[segment] = moveTail(rope[segment - 1], rope[segment])
                }

                tailPositions.add(rope.last())
            }
        }

        println(tailPositions.size)
    }

    part1()
    println("--------")
    part2()
}
