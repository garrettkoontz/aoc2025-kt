package com.k00ntz.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {

    val d = Day4()
    val input = """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.""".split("\n").map(String::toCharArray)

    @Test
    fun part1() {
        assertEquals(13, d.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(43, d.part2(input))
    }

}