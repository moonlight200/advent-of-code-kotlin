package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile
import kotlin.math.abs


fun main() {
    fun part1() {
        val input = readFile("input.txt", 10)

        var x = 1
        var cycle = 0
        val interestingSignals: MutableList<Pair<Int, Int>> = mutableListOf()

        fun incCycleCapturingSignal() {
            cycle++

            if ((cycle - 20) % 40 == 0) {
                interestingSignals.add(cycle to x)
            }
        }

        input.forEach { line ->
            when {
                line == "noop" -> {
                    incCycleCapturingSignal()
                }

                line.startsWith("addx") -> {
                    for (i in 1..2) {
                        incCycleCapturingSignal()
                    }
                    x += line.substring(5).toInt()
                }

                else -> throw IllegalArgumentException("Unknown instruction: $line")
            }
        }

        println(interestingSignals.sumOf { (cycle, signalStrength) -> cycle * signalStrength })
    }

    fun part2() {
        val input = readFile("input.txt", 10)

        var x = 1
        var cycle = 0

        fun incCycleAndPrintOnScreen() {
            print(if (abs((cycle % 40) - x) <= 1) '#' else '.')
            cycle++
            if (cycle % 40 == 0) {
                println()
            }
        }

        input.forEach { line ->
            when {
                line == "noop" -> {
                    incCycleAndPrintOnScreen()
                }

                line.startsWith("addx") -> {
                    for (i in 1..2) {
                        incCycleAndPrintOnScreen()
                    }
                    x += line.substring(5).toInt()
                }

                else -> throw IllegalArgumentException("Unknown instruction: $line")
            }
        }
    }

    part1()
    println("--------")
    part2()
}
