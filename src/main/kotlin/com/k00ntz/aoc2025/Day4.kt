package com.k00ntz.aoc2025

import com.k00ntz.aoc2025.util.Point
import com.k00ntz.aoc2025.util.getPoint
import com.k00ntz.aoc2025.util.setPoint
import com.k00ntz.aoc2025.util.validAroundNeighbors
import kotlin.collections.indices

fun main() {
    val d = Day4()
    d.run()
}

class PaperWarehouse(val map: List<CharArray>) {

    val accessibleThreshold: Int = 4
    val points = map.indices.flatMap { i -> map[i].indices.map { j -> Point(j, i) } }

    fun getAccessiblePoints(): List<Point> =
        points.filter { map.getPoint(it) == '@' }
            .filter { it.validAroundNeighbors(map) { it == '@' }.size < accessibleThreshold }

    fun removeAccessiblePoints(): Int {
        val ap = getAccessiblePoints()
        ap.forEach { map.setPoint(it, 'x') }
        return ap.size
    }
}


class Day4 {
    private val fileName = "day4.txt"

    private fun parse(filename: String): List<CharArray> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { it.toCharArray() }
        }
        throw RuntimeException("unable to find file Day4")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<CharArray>): Int {
        val points = input.indices.flatMap { i -> input[i].indices.map { j -> Point(j, i) } }
        val adjacents =
            points.filter { input.getPoint(it) == '@' }
                .map { it.validAroundNeighbors(input) { it == '@' }.size }
        return adjacents.count { it < 4 }
    }

    fun part2(input: List<CharArray>): Int {
        val pw = PaperWarehouse(input)
        var ap = pw.getAccessiblePoints()
        var cnt = 0
        while (ap.isNotEmpty()) {
            cnt += pw.removeAccessiblePoints()
            ap = pw.getAccessiblePoints()
        }
        return cnt
    }
}