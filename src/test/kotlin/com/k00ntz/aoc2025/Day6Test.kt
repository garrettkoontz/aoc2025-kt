package com.k00ntz.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test {
    val d = Day6()
    val input = parseProblems("""123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """.split("\n"))

    @Test
    fun part1() {
        assertEquals(4277556, d.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(3263827, d.part2(input))
    }

}