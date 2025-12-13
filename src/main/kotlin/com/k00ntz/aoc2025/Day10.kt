package com.k00ntz.aoc2025

import com.k00ntz.aoc2025.util.firstOrNullIndexed
import java.lang.RuntimeException

fun main() {
    val d1 = Day10()
    d1.run()
}

data class ButtonSpec(
    val indicators: Int,
    val wiring: List<Int>,
    val joltages: List<Int>
) {
    companion object {
        //[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        val regex = "\\[(.*)](.*)\\{(.*)}".toRegex()
        val wiringRegex = "\\(([0-9])[,?]\\)".toRegex()
        fun parse(s: String): ButtonSpec {
            val m = regex.matchEntire(s)
            m!!.destructured.let { (i, w, j) ->
                val ind = i.flatMapIndexed { index, ch -> if (ch == '#') listOf(index) else emptyList() }
                    .map { w2 -> 1 shl w2 }
                val indicators = ind.sum()
                val wiring = w.trim().split(' ')
                    .map { m ->
                        val w1 = m.trim('(', ')')
                        val w2 = w1.split(",")
                        val w3 = w2.map { w2 -> 1 shl w2.toInt() }
                        w3.sum()
                    }
                val joltages = j.trim('{', '}').split(',').map { it.toInt() }
                return ButtonSpec(indicators, wiring, joltages)
            }
        }
    }

    fun minConfigure(): Int {
        val queue = ArrayDeque(wiring.map { Pair(0, 0) })
        var i = 0
        val limit = 1000000
        while (i < limit) {
            val (c, w) = queue.removeFirst()
            val news = wiring.map { Pair(c + 1, w xor it) }
            if (news.any { it.second == indicators }) {
                return c + 1
            } else {
                queue.addAll(news)
            }
            i++
        }
        throw RuntimeException("Unable to find a configuration within $limit")
    }

    fun List<Int>.sumWith(other: List<Int>): List<Int> =
        this.mapIndexed { index, i -> i + other[index] }

    fun List<Int>.timesWith(other:List<Int>): List<Int> =
        this.mapIndexed { index, i -> i * other[index] }

    fun joltages(): Int {
        val updateButtons =
            wiring.map {
                it.toString(2)
                    .toInt()
                    .toString()
                    .reversed()
                    .padEnd(joltages.size, '0')
                    .map { it - '0' }
            }


        return 0
    }
}


class Day10 {
    private val fileName = "day10.txt"

    private fun parse(filename: String): List<ButtonSpec> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { ButtonSpec.parse(it) }
        }
        throw RuntimeException("unable to find file Day10")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<ButtonSpec>): Int {
        return input.sumOf { it.minConfigure() }
    }

    fun part2(input: List<ButtonSpec>): Int {
        return input.sumOf { it.joltages() }
    }
}