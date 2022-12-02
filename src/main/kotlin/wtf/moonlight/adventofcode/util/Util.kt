package wtf.moonlight.adventofcode.util

import java.io.FileNotFoundException
import java.io.InputStream

private class Resources

private fun openInputStream(name: String, day: Int, year: Int): InputStream {
    val path = "/%d/%02d/%s".format(year, day, name)
    return Resources::class.java.getResourceAsStream(path)
        ?: throw FileNotFoundException("Resource '$path' not found")
}

fun readFile(name: String, day: Int, year: Int = 2022): List<String> =
    openInputStream(name, day, year).bufferedReader().readLines()

fun loadFileAsSequence(name: String, day: Int, year: Int = 2022): Sequence<String> =
    openInputStream(name, day, year).bufferedReader().lineSequence()
