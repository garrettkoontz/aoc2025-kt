package com.k00ntz.aoc2025

fun main() {
    val d = Day2()
    d.run()
}

fun isRepeatedTwice(l: Long): Boolean {
    val s = l.toString()
    return s.take(s.length / 2) == s.drop(s.length / 2)
}

fun isRepeated(l: Long): Boolean {
    val s = l.toString()
    return (1..s.length / 2).any {
        if (s.length % it == 0) {
            val w = s.windowed(it, it)
            val f = w.first()
            w.drop(1).all { it == f }
        } else false
    }
}

data class Range(val start: String, val end: String) {
    companion object {
        fun parse(s: String): List<Range> =
            s.split(",").map {
                val s = it.split("-")
                Range(s[0], s[1])
            }
    }

    fun findInvalidValues(divisor: Int = 2): List<Long> {
        if (start.length != end.length) {
            return (start.toLong()..end.toLong()).filter { isRepeatedTwice(it) }
        } else {
            val endLong = end.toLong()
            val startLong = start.toLong()
            val f = start.take(start.length / divisor)
            if (f.isNotEmpty() && (f + f).toLong() > end.toLong()) {
                return emptyList()
            }
            val b = end.take(end.length / divisor)
            return (f.toInt()..b.toInt())
                .map { it.toString().repeat(divisor).toLong() }
                .dropWhile { it < startLong }
                .takeWhile { it <= endLong }
        }
    }

    fun findInvalidValues2(takeSize: Int): List<Long> {
        if (start.length != end.length) {
            return (start.toLong()..end.toLong()).filter { isRepeated(it) }
        } else {
            if (end.length / takeSize == 0) {
                return emptyList()
            }
            val endLong = end.toLong()
            val startLong = start.toLong()
            val f = start.take(takeSize)
            if (f.isNotEmpty() && f.repeat(end.length / takeSize).toLong() > end.toLong()) {
                return emptyList()
            }
            val b = end.take(takeSize)
            return (f.toInt()..b.toInt())
                .map { it.toString().repeat(end.length / takeSize).toLong() }
                .dropWhile { it < startLong }
                .takeWhile { it <= endLong }
        }
    }

    fun findInvalidValues3(): List<Long> {
        return (start.toLong()..end.toLong()).filter { isRepeated(it) }
    }
}

class Day2 {
    private val fileName = "day2.txt"

    private fun parse(filename: String): List<Range> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return Range.parse(raw.first())
        }
        throw RuntimeException("unable to find file Day2")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Range>): Long {
        val a = input.flatMap {
            it.findInvalidValues()
        }
        return a.sum()
    }

    fun part2(input: List<Range>): Long {
        val i = input.flatMap { i ->
            i.findInvalidValues3()
        }
        val j = input.flatMap { i ->
            (1..i.end.length / 2).flatMap {
                i.findInvalidValues2(it)
            }.toSet()
        }
        return i.sum()


    }
}

//27469618483 too high
//27469417404