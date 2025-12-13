package com.k00ntz.aoc2025

import com.k00ntz.aoc2025.util.getAllIndicesOf
import kotlin.collections.iterator

fun main() {
    val d = Day7()
    d.run()
}

data class Tachyon(val start: Int, val splitters: List<Set<Int>>) {
    companion object {
        fun parse(input: List<String>): Tachyon {
            val splitters = input.map { it.toList().getAllIndicesOf('^').toSet() }.drop(1)
            val start = input.first().indexOfFirst { it == 'S' }
            return Tachyon(start, splitters)
        }
    }

    fun run(): Pair<Set<Int>, Int> {
        val end = splitters.fold(Pair(setOf(start), 0)) { acc, set ->
            val tachyons = acc.first
            var countSplits = acc.second
            val newTachyons =
                tachyons.flatMap { if (set.contains(it)) listOf(it - 1, it + 1).also { countSplits++ } else listOf(it) }
            Pair(newTachyons.toSet(), countSplits)
        }
        return end
    }

    fun runSplitTime(): Map<Int, Long> {
        val end = splitters.fold(mapOf(start to 1L)) { acc, set ->
            val pairs = mutableListOf<Pair<Int, Long>>()
            for (splitter in acc) {
                if (set.contains(splitter.key)) {
                    pairs.addAll(
                        listOf(
                            Pair(splitter.key - 1, splitter.value),
                            Pair(splitter.key + 1, splitter.value)
                        )
                    )
                } else {
                    pairs.add(Pair(splitter.key, splitter.value))
                }
            }
            pairs.groupBy { it.first }
                .mapValues { entry -> entry.value.sumOf { it.second } }

        }
        return end
    }
}

class Day7 {
    private val fileName = "day7.txt"

    private fun parse(filename: String): Tachyon {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return Tachyon.parse(raw)
        }
        throw RuntimeException("unable to find file Day7")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: Tachyon): Int {
        return input.run().second
    }

    fun part2(input: Tachyon): Long {
        return input.runSplitTime().values.sum()
    }
}

// too low 489421843