package com.k00ntz.aoc2025

import java.util.SortedSet

fun main() {
    val d = Day5()
    d.run()
}

data class KitchenIngredient(val ranges: List<LongRange>, val ingredients: List<Long>) {
    val consolidatedRanges = getFreshRanges()

    fun countFresh(): Int =
        ingredients.count { i -> ranges.any { r -> r.contains(i) } }

    private fun getFreshRanges(): SortedSet<LongRange> {
        return ranges.drop(1)
            .fold(
                setOf(ranges.first())
                    .toSortedSet { o1, o2 -> o1.first.compareTo(o2.first) }) { acc, longs ->
                acc.addRange(longs)
            }

    }

    private fun SortedSet<LongRange>.addRange(other: LongRange): SortedSet<LongRange> {
        val first = other.first
        val last = other.last
        val containsFirst = this.firstOrNull { it.contains(first) }
        val containsLast = this.firstOrNull { it.contains(last) }
        if (containsFirst != null && containsLast != null) {
            // This new range is completely contained within a current range
            if (containsFirst == containsLast) {
                return this
            } else {
                // This new range connects a gap between other existing ranges
                val removes = this.subSet(containsFirst, containsLast)
                removes.forEach { this.remove(it) }
                this.remove(containsLast)
                this.add(containsFirst.first..containsLast.last)
                return this
            }
        }
        // This new range is on one edge of the current ranges
        if (containsFirst != null) {
            val subSet = this.tailSet(containsFirst)
            val (removes, _) = subSet.partition { it.first < last }
            removes.forEach { this.remove(it) }
            this.add(containsFirst.first..last)
            return this
        } else if (containsLast != null) {
            // containsLast != null
            val subSet = this.headSet(containsLast)
            val (removes, _) = subSet.partition { it.last > first }
            removes.forEach { this.remove(it) }
            this.remove(containsLast)
            this.add(first..containsLast.last)
            return this
        } else {
            val removes = this.filter { it.first > first && it.last < last }
            removes.forEach { this.remove(it) }
            this.add(first..last)
            return this
        }
    }

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

    fun part2(input: KitchenIngredient): Long =
        input.consolidatedRanges.sumOf { it.last - it.first + 1 }
}

private fun SortedSet<LongRange>.containsValue(first: Long): Boolean =
    this.any { it.contains(first) }
