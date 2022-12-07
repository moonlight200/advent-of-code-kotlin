package wtf.moonlight.adventofcode.y2022

import wtf.moonlight.adventofcode.util.readFile


sealed class FileSystem(val name: String) : Comparable<FileSystem> {
    abstract val path: String

    abstract fun getSize(): Long

    abstract fun walk(depth: Int = 0, block: (FileSystem, Int) -> Unit)

    override fun compareTo(other: FileSystem): Int =
        if (this is Directory && other is File) -1
        else if (this is File && other is Directory) 1
        else this.name.compareTo(other.name)

    fun print() {
        walk { file, depth ->
            println(buildString {
                for (i in 0 until depth)
                    append("  ")
                append("- ")
                append(file.name)
                when (file) {
                    is Directory -> append(" (dir)")
                    is File -> {
                        append(" (file, size=")
                        append(file.getSize())
                        append(')')
                    }
                }
            })
        }
    }
}

class File(
    name: String,
    private val size: Long
) : FileSystem(name) {
    override val path: String
        get() = name

    override fun getSize(): Long = size

    override fun walk(depth: Int, block: (FileSystem, Int) -> Unit) {
        block(this, depth)
    }
}

class Directory(name: String, val parent: Directory?) : FileSystem(name) {
    private val contents: MutableList<FileSystem> = ArrayList()

    override val path: String
        get() = parent?.path?.let { "$it/$name" } ?: ""

    operator fun get(name: String): FileSystem? =
        contents.find { it.name == name }

    fun add(file: FileSystem) {
        contents.add(file)
        contents.sort()
    }

    override fun getSize(): Long =
        contents.sumOf { it.getSize() }

    override fun walk(depth: Int, block: (FileSystem, Int) -> Unit) {
        block(this, depth)
        contents.forEach {
            if (it is Directory)
                it.walk(depth + 1, block)
            else
                block(it, depth + 1)
        }
    }
}

fun main() {
    fun buildFileSystem(input: List<String>): Directory {
        val root = Directory("/", null)
        var currentDir: Directory = root

        input.forEach {
            if (it.startsWith("$")) {
                val parts = it.removePrefix("$").trim().split(" ")
                when (parts.first()) {
                    "cd" -> {
                        when (val dir = parts.last()) {
                            "/" -> currentDir = root
                            ".." -> currentDir = currentDir.parent ?: root
                            else -> {
                                val next = currentDir[dir]
                                    ?: throw IllegalArgumentException("Cannot navigate to '$dir', directory not found")
                                if (next !is Directory)
                                    throw IllegalArgumentException("Cannot navigate to '$dir', it is not a directory")
                                currentDir = next
                            }
                        }
                    }

                    "ls" -> {}
                    else -> throw NotImplementedError("Unknown command $parts")
                }
            } else {
                // Parse content
                val (size, name) = it.split(" ", limit = 2)
                if (size == "dir") {
                    currentDir.add(Directory(name, currentDir))
                } else {
                    currentDir.add(File(name, size.toLong()))
                }
            }
        }

        return root
    }

    fun part1() {
        val input = readFile("input.txt", 7)
        val root = buildFileSystem(input)

        var sumSize = 0L
        root.walk { file, _ ->
            if (file !is Directory) return@walk
            val size = file.getSize()
            if (size > 100000) return@walk
            sumSize += size
        }
        println(sumSize)
    }

    fun part2() {
        val input = readFile("input.txt", 7)
        val root = buildFileSystem(input)

        val requiredSpace = 30_000_000L - (70_000_000L - root.getSize())
        println("Need to free $requiredSpace")
        var deletionCandidate = root to Long.MAX_VALUE

        root.walk { file, _ ->
            if (file !is Directory) return@walk
            val size = file.getSize()
            if (size < requiredSpace) return@walk
            if (size < deletionCandidate.second) deletionCandidate = file to size
        }

        println("Found directory ${deletionCandidate.first.path} with size ${deletionCandidate.second} as the optimal deletion candidate")
    }

    part1()
    println("--------")
    part2()
}
