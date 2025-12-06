package com.k00ntz.aoc2025

import com.k00ntz.com.k00ntz.aoc2025.Day5
import com.k00ntz.com.k00ntz.aoc2025.KitchenIngredient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Test {
    val d = Day5()
    val input = KitchenIngredient(
        ranges = listOf(
            (3L..5L),
            (10L..14L),
            (16L..20L),
            (12L..18L),
        ),
        ingredients = listOf(
            1L,
            5L,
            8L,
            11L,
            17L,
            32L
        )
    )

    @Test
    fun part2() {
        assertEquals(14L, d.part2(input))
    }

}