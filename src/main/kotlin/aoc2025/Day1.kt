package com.k00ntz.aoc2025

import java.lang.RuntimeException
import java.math.BigInteger

fun main() {
    val d1 = Day1()
    d1.run()
}

data class Day1Record() {
    companion object {
        fun parse(s: String): Day1Record {
            return Day1Record()
        }
    }
}

class Day1 {
    private val fileName = "day1.txt"

    private fun parse(filename: String): List<Day1Record> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { Day1Record.parse(it) }
        }
        throw RuntimeException("unable to find file Day1")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<ConditionRecord>): BigInteger {
        return TODO()
    }

    fun part2(input: List<ConditionRecord>): BigInteger {
        return TODO()
    }
}