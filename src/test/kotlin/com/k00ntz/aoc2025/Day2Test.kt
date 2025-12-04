package com.k00ntz.aoc2025

import com.k00ntz.com.k00ntz.aoc2025.Day2
import com.k00ntz.com.k00ntz.aoc2025.Range
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test {
    @Test
    fun findInvalidValues() {
        val r1 = Range("1", "12")
        val r2 = Range("11", "99")
        val r3 = Range("12342345", "12342346")
        val r4 = Range("9911", "9998")

        assertEquals(listOf(11L), r1.findInvalidValues())
        assertEquals(emptyList<Long>(), r3.findInvalidValues())
        assertEquals(emptyList<Long>(), r4.findInvalidValues())
        assertEquals(listOf(11L, 22L, 33L, 44L, 55L, 66L, 77L, 88L, 99L), r2.findInvalidValues())
    }

    @Test
    fun day2Part1() {
        val d = Day2()
        val input = Range.parse(
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                    "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                    "824824821-824824827,2121212118-2121212124"
        )
        assertEquals(1227775554, d.part1(input))
    }

    @Test
    fun day2Part2() {
        val d = Day2()
        val input = Range.parse(
            "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
                    "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
                    "824824821-824824827,2121212118-2121212124"
        )
        assertEquals(4174379265, d.part2(input))
    }

}