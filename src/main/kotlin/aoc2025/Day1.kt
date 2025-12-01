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
}

class Safe(private var position: Int = 50, private val limit: Int = 100) {
    fun applyRotation(rotation: Rotation): Int {
        position = (limit + position + ((rotation.quantity) * rotation.direction.multiplier)) % limit
        return position
    }

    fun applyRotationTrackSecret(rotation: Rotation): Int {
        val oldPosition = position
        val newPos = (position + ((rotation.quantity) * rotation.direction.multiplier))
        applyRotation(rotation)
        val rng = IntProgression.fromClosedRange(oldPosition, newPos, rotation.direction.multiplier)
        val cnt = rng.drop(1).count { it % limit == 0 }
        return cnt
    }

    fun applyRotationsWithSecret(rotations: List<Rotation>, secretKey: Int): Int {
        return rotations.map { applyRotation(it) }.count { it == secretKey }
    }

    fun applyRotationsCountCrossSecret(rotations: List<Rotation>, secretKey: Int): Int {
        val rots = rotations.map { applyRotationTrackSecret(it) }
        return rots.sum()
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
        return safe.applyRotationsWithSecret(input, 0)
    }

    fun part2(input: List<Rotation>): Int {
        val safe = Safe()
        return safe.applyRotationsCountCrossSecret(input, 0)
    }
}