package aoc2025

import com.k00ntz.aoc2025.Day1
import com.k00ntz.aoc2025.Rotation
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day1Test {
    val d1 = Day1()
    val d1Input = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""".split("\n").map { Rotation.parse(it) }

    @Test
    fun part1() {
        assertEquals(3, d1.part1(d1Input))
    }

    @Test
    fun part2() {
        assertEquals(6, d1.part2(d1Input))
    }

    @Test
    fun part22() {
        assertEquals(9, d1.part2(d1Input.plus(Rotation.parse("L300"))))
    }

}