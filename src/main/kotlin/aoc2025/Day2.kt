package com.k00ntz.aoc2025

fun main() {
    val d = Day2()
    d.run()
}



class Day2 {
    private val fileName = "day2.txt"

    private fun parse(filename: String): List<Rotation> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { Rotation.parse(it) }
        }
        throw RuntimeException("unable to find file Day2")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Rotation>): Int {
         TODO()
    }

    fun part2(input: List<Rotation>): Int {
         TODO()
    }
}