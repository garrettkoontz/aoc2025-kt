package com.k00ntz.aoc2025

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10Test {
    val d = Day10()
    val input = """[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""".split("\n").map { ButtonSpec.parse(it) }

    @Test
    fun part1() {
        assertEquals(7, d.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(33, d.part2(input))
    }

}