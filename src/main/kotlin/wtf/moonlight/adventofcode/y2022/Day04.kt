package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile


fun main() {
    operator fun IntRange.contains(other: IntRange): Boolean =
        other.first in this && other.last in this

    infix fun IntRange.overlaps(other: IntRange): Boolean =
        other.first in this || other.last in this

    fun part1() {
        val input = readFile("input.txt", 4)
        val count = input.count {
            val (r1, r2) = it.split(",").map { range ->
                val (start, end) = range.split("-").map { nr -> nr.toInt() }
                start..end
            }

            r1 in r2 || r2 in r1
        }
        println(count)
    }

    fun part2() {
        val input = readFile("input.txt", 4)
        val count = input.count {
            val (r1, r2) = it.split(",").map { range ->
                val (start, end) = range.split("-").map { nr -> nr.toInt() }
                start..end
            }

            r1 overlaps r2 || r2 overlaps r1
        }
        println(count)
    }

    part1()
    println("--------")
    part2()
}
