package com.k00ntz.aoc2025

import com.k00ntz.aoc2025.Direction.L
import com.k00ntz.aoc2025.Direction.R
import java.lang.RuntimeException

fun main() {
    val d1 = Day1()
    d1.run()
}

enum class Direction(val multiplier: Int) {
    L(-1), R(1)
}


fun parseDirection(c: Char): Direction {
    return when (c) {
        'L' -> L
        'R' -> R
        else -> throw RuntimeException("Invalid direction $c")
    }
}


data class Rotation(
    val direction: Direction,
    val quantity: Int
) {
    companion object {
        fun parse(s: String): Rotation {
            return Rotation(parseDirection(s[0]), s.substring(1).toInt())
        }
    }

    fun apply(i: Int): Int =
        i + ((quantity) * direction.multiplier)
}

class Safe(private var position: Int = 50, private val limit: Int = 100) {
    fun rotate(rotation: Rotation): Int {
        position = rotation.apply(limit + position) % limit
        return position
    }

    fun rotationTrackSecret(rotation: Rotation): Int {
        val newPos = rotation.apply(position)
        return IntProgression.fromClosedRange(position, newPos, rotation.direction.multiplier).drop(1)
            .count { it % limit == 0 }.also {
                rotate(rotation)
            }
    }

    fun countSecrets(rotations: List<Rotation>, secretKey: Int): Int {
        return rotations.count { rotate(it) == secretKey }
    }

    fun countCrossSecrets(rotations: List<Rotation>, secretKey: Int): Int {
        return rotations.sumOf { rotationTrackSecret(it) }
    }

}


class Day1 {
    private val fileName = "day1.txt"

    private fun parse(filename: String): List<Rotation> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return raw.map { Rotation.parse(it) }
        }
        throw RuntimeException("unable to find file Day1")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Rotation>): Int {
        val safe = Safe()
        return safe.countSecrets(input, 0)
    }

    fun part2(input: List<Rotation>): Int {
        val safe = Safe()
        return safe.countCrossSecrets(input, 0)
    }
}