package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile


fun main() {
    fun part1() {
        val input = readFile("input.txt", 6)
        input.forEach {
            for (start in 0 until it.length - 4) {
                val candidate = it.substring(start, start + 4)
                if (candidate.toSet().size == candidate.length) {
                    println(start + 4)
                    break
                }
            }
        }
    }

    fun part2() {
        val input = readFile("input.txt", 6)
        input.forEach {
            for (start in 0 until it.length - 14) {
                val candidate = it.substring(start, start + 14)
                if (candidate.toSet().size == candidate.length) {
                    println(start + 14)
                    break
                }
            }
        }
    }

    part1()
    println("--------")
    part2()
}
