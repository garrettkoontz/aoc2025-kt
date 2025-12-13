package com.k00ntz.aoc2025

fun main() {
    val d = Day4()
    d.run()
}

data class BatteryBank(val joltages: String) {

    private val joltagePairs =
        joltages.mapIndexed { i, c -> Pair(i, c - '0') }

    companion object {
        fun parse(s: String): BatteryBank {
            return BatteryBank(s)
        }
    }

    fun findLargest(): Int {
        val t = joltages.dropLast(1).max()
        val idxF = joltages.indexOf(t)
        val o = joltages.substring(idxF + 1).max()
        return "$t$o".toInt()
    }

    fun findNthLargest(n: Int): Long {
        return findNthLargestDP(n).toNumberString()
    }

    private fun List<Pair<Int,Int>>.toNumberString(): Long =
        this.sortedBy { it.first }.joinToString(separator = "") { it.second.toString() }.toLong()

    private fun findNthLargestDP(n: Int): List<Pair<Int, Int>> {
        if (n < 1) throw RuntimeException("negative n")
        if (n == 1) {
            val m = joltagePairs.maxBy { it.second}
            return listOf(m)
        } else {
            val nm1 = findNthLargestDP(n - 1)
            val rest = joltagePairs.minus(nm1.toSet())
            return nm1.plus(rest.maxBy { nm1.plus(it).toNumberString() })
        }
    }
}

class Day3 {
    private val fileName = "day3.txt"

    private fun parse(filename: String): List<BatteryBank> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { BatteryBank.parse(it) }
        }
        throw RuntimeException("unable to find file Day3")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<BatteryBank>): Int =
        input.sumOf { it.findLargest() }

    fun part2(input: List<BatteryBank>): Long =
        input.sumOf { it.findNthLargest(12) }
}