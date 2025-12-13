package com.k00ntz.aoc2025

fun main() {
    val d = Day6()
    d.run()
}

enum class Op(val fn: (List<Long>) -> Long) {
    Plus({ it.sum() }), Times({ it.reduce { acc, i -> acc * i } })
}

data class Problem(val op: Op, val numbers: List<Long>, val strings: List<String>, val length: Int) {
    fun solve(): Long {
        return op.fn(numbers)
    }

    fun cephalopodSolve(): Long {
        val extract = (0 until length).map { i -> strings.map { it[i] } }.filterNot { it.all { it == ' ' } }
        val nums = extract.map { it.joinToString("").trim().toLong() }
        return op.fn(nums)
    }
}

fun parseProblems(lines: List<String>): List<Problem> {
    val blankIdxs = lines.last().flatMapIndexed { index, ch -> if (ch != ' ') listOf(index) else emptyList() }
    val ranges = blankIdxs.plus(
        listOf(lines[0].length)
    ).zipWithNext()
    val splits = lines.map { line -> line.split(" ").filterNot { it.isEmpty() } }
    val idx = splits.first().size
    return (0 until idx).map { i ->
        val values = splits.map { it[i] }
        val op = values.last().let { if (it == "*") Op.Times else Op.Plus }
        val numbers = values.dropLast(1).map { it.toLong() }
        Problem(op, numbers, lines.dropLast(1).map { l ->
            ranges[i].let { idxs ->
                l.substring(idxs.first, idxs.second)
            }
        }, ranges[i].let { it.second - it.first })
    }
}

class Day6 {
    private val fileName = "day6.txt"

    private fun parse(filename: String): List<Problem> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return parseProblems(raw)
        }
        throw RuntimeException("unable to find file Day6")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Problem>): Long {
        return input.sumOf { it.solve() }
    }

    fun part2(input: List<Problem>): Long {
        return input.sumOf { it.cephalopodSolve() }
    }
}
