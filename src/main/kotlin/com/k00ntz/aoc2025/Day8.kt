package com.k00ntz.com.k00ntz.aoc2025

import com.k00ntz.aoc2025.util.Point3D
import com.k00ntz.aoc2025.util.distanceTo
import com.k00ntz.aoc2025.util.x
import java.util.Collections
import java.util.NavigableMap
import java.util.SortedMap

fun main() {
    val d = Day8()
    d.run()
}

class JunctionBoxes(private val pts: List<Point3D>) {

    val connections: MutableMap<Point3D, Int?> = pts.associateWith { null }.toMutableMap()

    val distanceMap = createDistanceMap()

    fun sizeList() = connections.entries.map { (k, v) -> v to k }.groupBy({ it.first }, { it.second })

    private fun createDistanceMap(): NavigableMap<Double, Pair<Point3D, Point3D>> {
        val entries = connections.keys.toList()
        val m = (0 until entries.size).flatMap { idx ->
            val entry1 = entries[idx]
            entries.drop(idx + 1).mapNotNull { entry2 ->
                if (entry1 != entry2) {
                    Pair(Pair(entry1, entry2), entry1.distanceTo(entry2))
                } else {
                    null
                }
            }
        }.associateBy({ it.second }, { it.first }).toSortedMap()
        return m as NavigableMap<Double, Pair<Point3D, Point3D>>
    }

    private var circuitCounter: Int = 0

    fun findNextSmallestDistance(greaterThan: Double): Pair<Double, Pair<Point3D, Point3D>> {
        val next = distanceMap.tailMap(greaterThan, false).firstEntry()
        if (next != null) {
            return next.toPair()
        } else {
            throw RuntimeException()
        }
    }

    fun connect(point1: Point3D, point2: Point3D): Int {
        val pv1 = connections[point1]
        val pv2 = connections[point2]
        if (pv1 != null) {
            if (pv1 == pv2) {
                return pv1
            }
            if (pv2 != null) {
                // connect the two circuit together
                val newValue = pv1
                val convertPoints = pv2.let { value -> connections.filter { it.value == value } }
                convertPoints.forEach {
                    connections[it.key] = newValue
                }
                return pv1
            } else {
                connections[point2] = pv1
                return pv1
            }

        } else if (pv2 != null) {
            // connect the two circuit together
            connections[point1] = pv2
            return pv2
        } else {
            // neither are currently connected
            val i = circuitCounter++
            connections[point2] = i
            connections[point1] = i
            return i
        }
    }

    fun wireUp(limit: Int? = null): Pair<Point3D, Point3D>? {
        if (circuitCounter != 0) {
            throw RuntimeException("Already wired up!")
        }
        var currentSmallestDistance = 0.0
        var i = 0
        val l = limit ?: 1000000
        while (i < l) {
            val (distance, pairs) = findNextSmallestDistance(currentSmallestDistance)
            val (a,b) = pairs
            connect(a,b)
            currentSmallestDistance = distance
            i++
            if (this.connections.values.toSet().size == 1)
                return pairs
        }
        return null
    }

}


fun parse3D(input: List<String>): List<Point3D> =
    input.map { line -> line.split(",").let { (x, y, z) -> Triple(x.toInt(), y.toInt(), z.toInt()) } }

class Day8 {
    private val fileName = "day8.txt"

    private fun parse(filename: String): List<Point3D> {
        val raw = ClassLoader.getSystemResourceAsStream(filename)?.bufferedReader()?.readLines()
        if (raw != null) {
            return parse3D(raw)
        }
        throw RuntimeException("unable to find file Day8")
    }

    fun run() {
        val input = parse(fileName)
        println(part1(input))
        println(part2(input))
    }

    fun part1(input: List<Point3D>, connectionLimit: Int = 1000): Int {
        val j = JunctionBoxes(input)
        j.wireUp(connectionLimit)
        val n = j.sizeList().filterKeys { it != null }.values.map { it.size }.sorted().takeLast(3)
        return n[0] * n[1] * n[2]
    }

    fun part2(input: List<Point3D>): Int {
        val j = JunctionBoxes(input)
        val p = j.wireUp()
        if (p != null) {
            return p.first.x() * p.second.x()
        }
        return -1;
    }
}
