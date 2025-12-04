package com.k00ntz.aoc2025

import com.k00ntz.aoc3035.BatteryBank
import com.k00ntz.aoc3035.Day3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {

    val d = Day3()
    val input = ("987654321111111\n" +
            "811111111111119\n" +
            "234234234234278\n" +
            "818181911112111").split("\n").map {
        BatteryBank(it)
    }

    @Test
    fun part1() {
        assertEquals(357, d.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(3121910778619, d.part2(input))
    }

}