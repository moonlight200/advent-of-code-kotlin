package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile
import kotlin.math.max


fun main() {
    fun List<String>.parseStacks(): Array<MutableList<Char>> {
        val stackCount = last().trim().split("   ").size
        val stacks = Array<MutableList<Char>>(stackCount) { ArrayList(size - 1) }

        reversed().drop(1).forEach { line ->
            line.chunked(4) {
                val crate = it.trim()
                if (crate.isBlank())
                    null
                else if (crate.startsWith('[') && crate.endsWith(']'))
                    crate[1]
                else
                    throw IllegalArgumentException("Cannot parse crate from '$it'")
            }.forEachIndexed { index, crate ->
                if (crate == null) return@forEachIndexed
                stacks[index].add(crate)
            }
        }

        return stacks
    }

    fun printStacks(stacks: Array<MutableList<Char>>) {
        val linesCount = stacks.fold(0) { acc, stack -> max(acc, stack.size) } + 1
        List(linesCount) { crateIndex ->
            stacks.joinToString(separator = " ") { stack ->
                stack.getOrNull(crateIndex)?.let { "[$it]" } ?: "   "
            }
        }
            .reversed()
            .forEach {
                println(it)
            }
        println(stacks.mapIndexed { index, _ -> " ${index + 1} " }.joinToString(separator = " "))
    }

    fun move9000(stacks: Array<MutableList<Char>>, count: Int, from: Int, to: Int) {
        for (i in 0 until count) {
            stacks[to - 1].add(stacks[from - 1].removeLast())
        }
    }

    fun move9001(stacks: Array<MutableList<Char>>, count: Int, from: Int, to: Int) {
        stacks[to - 1].addAll(stacks[from - 1].takeLast(count))
        for (i in 0 until count) {
            stacks[from - 1].removeLast()
        }
    }

    fun part1() {
        val input = readFile("input.txt", 5)
        val stacks = input.takeWhile { it.isNotBlank() }.parseStacks()

        input.dropWhile {
            !it.startsWith("move")
        }.forEach {
            val parts = it.split(" ")
            if (parts.size != 6) throw IllegalArgumentException("Unknown instruction '$it'")
            move9000(
                stacks = stacks,
                count = parts[1].toInt(),
                from = parts[3].toInt(),
                to = parts[5].toInt()
            )
        }

        printStacks(stacks)
        println()
        println(stacks.joinToString(separator = "") { it.last().toString() })
    }

    fun part2() {
        val input = readFile("input.txt", 5)
        val stacks = input.takeWhile { it.isNotBlank() }.parseStacks()

        input.dropWhile {
            !it.startsWith("move")
        }.forEach {
            val parts = it.split(" ")
            if (parts.size != 6) throw IllegalArgumentException("Unknown instruction '$it'")
            move9001(
                stacks = stacks,
                count = parts[1].toInt(),
                from = parts[3].toInt(),
                to = parts[5].toInt()
            )
        }

        printStacks(stacks)
        println()
        println(stacks.joinToString(separator = "") { it.last().toString() })
    }

    part1()
    println("--------")
    part2()
}
