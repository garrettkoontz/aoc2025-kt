package com.k00ntz.com.k00ntz.aoc2025

fun main() {
    val d = Day5()
    d.run()
}

data class KitchenIngredient(val ranges: List<LongRange>, val ingredients: List<Long>){
    fun countFresh(): Int =
        ingredients.count { i -> ranges.any { r -> r.contains(i) } }

    fun getFreshRanges
}

fun parseIngredients(input: List<String>): KitchenIngredient {
    val splitIdx = input.indexOfFirst { it.isEmpty() }
    val ranges = input.subList(0, splitIdx).map { it.split("-").let { (it[0].toLong()..it[1].toLong()) } }
    val ingredients = input.subList(splitIdx + 1, input.size).map { it.toLong() }
    return KitchenIngredient(ranges, ingredients)
}

class Day5 {
    private val fileName = "day5.txt"

    private fun parse(filename: String): KitchenIngredient {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return parseIngredients(raw)
        }
        throw RuntimeException("unable to find file Day5")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: KitchenIngredient): Int {
        return input.countFresh()
    }

    fun part2(input: KitchenIngredient): Int {
        return 0
    }
}