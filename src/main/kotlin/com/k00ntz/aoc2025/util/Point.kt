package com.k00ntz.aoc2025.util

import java.util.*
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt


typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point): Point =
    Pair(this.first + other.first, this.second + other.second)

operator fun Point.minus(other: Point): Point =
    Pair(this.first - other.first, this.second - other.second)

operator fun Point.times(other: Point): Point =
    Pair(this.first * other.first, this.second * other.second)

operator fun Point.times(i: Int): Point =
    Pair(this.first * i, this.second * i)

fun Point.manhattanDistanceTo(point: Point): Int {
    return abs(this.x() - point.x()) + abs(this.y() - point.y())
}

fun <E> MutableList<MutableList<E>>.setPoint(pt: Point, e: E) {
    this[pt.y()][pt.x()] = e
}

fun List<CharArray>.setPoint(pt: Point, char: Char) {
    this[pt.y()][pt.x()] = char
}

fun <E> MutableList<MutableList<E>>.setPointFn(pt: Point, fn: (E) -> E) {
    this[pt.y()][pt.x()] = fn(this[pt.y()][pt.x()])
}

fun <T> Point.validBackNeighbors(map: List<List<T>>, matcher: (T) -> Boolean = { true }): Set<Point> =
    setOf(this + Point(0, -1), this + Point(-1, 0))
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

fun <T> Point.validForwardNeighbors(map: List<List<T>>, matcher: (T) -> Boolean = { true }): Set<Point> =
    setOf(this + Point(0, 1), this + Point(1, 0))
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

fun <T> Point.validCrossNeighbors(map: List<List<T>>, matcher: (T) -> Boolean = { true }): Set<Point> =
    setOf(this + Point(0, 1), this + Point(0, -1), this + Point(1, 0), this + Point(-1, 0))
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

@JvmName("validCrossNeighborsChars")
fun Point.validCrossNeighbors(map: List<CharArray>, matcher: (Char) -> Boolean = { true }): Set<Point> =
    setOf(this + Point(0, 1), this + Point(0, -1), this + Point(1, 0), this + Point(-1, 0))
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

@JvmName("validAroundNeighborsChars")
fun Point.validAroundNeighbors(map: List<CharArray>, matcher: (Char) -> Boolean = { true }): Set<Point> =
    setOf(
        this + Point(0, 1), this + Point(0, -1), this + Point(1, 0), this + Point(-1, 0),
        this + Point(1, 1), this + Point(1, -1), this + Point(-1, 1), this + Point(-1, -1)
    )
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

fun <T> Point.validAroundNeighbors(map: List<List<T>>, matcher: (T) -> Boolean = { true }): Set<Point> =
    setOf(
        this + Point(0, 1), this + Point(0, -1), this + Point(1, 0), this + Point(-1, 0),
        this + Point(1, 1), this + Point(1, -1), this + Point(-1, 1), this + Point(-1, -1)
    )
        .filter {
            it.y() >= 0 && it.y() < map.size
                    && it.x() >= 0 && it.x() < map[it.y()].size
                    && matcher(map.getPoint(it))
        }
        .toSet()

operator fun Point.compareTo(other: Point): Int =
    this.distanceTo(Point(0, 0)).compareTo(other.distanceTo(Point(0, 0)))

fun Point.distanceTo(point: Point): Double {
    val xDiff = (this.x() - point.x()).toDouble()
    val yDiff = (this.y() - point.y()).toDouble()
    return sqrt(xDiff * xDiff + yDiff * yDiff)
}

fun Point.angleTo(point: Point): Double {
    val vector2 = this - point
    val vector1 = Point(0, 1)
    return (atan2(vector2.y().toDouble(), vector2.x().toDouble())
            - atan2(
        vector1.y().toDouble(), vector1.x().toDouble()
    ) + 2 * PI) % (2 * PI)
}

fun Point.x(): Int =
    this.first

fun Point.y(): Int =
    this.second

fun Point.filterColinear(pt2: Point, others: List<Point>): List<Point> =
    others.filter { this.areColinear(pt2, it) }

fun Point.areColinear(pt2: Point, pt3: Point): Boolean =
    (this.x() - pt2.x()) * (pt2.y() - pt3.y()) == (pt2.x() - pt3.x()) * (this.y() - pt2.y())

fun Point.isBetween(pt1: Point, pt2: Point): Boolean =
    pt1.distanceTo(this) + this.distanceTo(pt2) == pt1.distanceTo(pt2)

fun ccw(p1: Point, p2: Point, p3: Point) =
    (p2.x() - p1.x()) * (p3.y() - p1.y()) - (p2.y() - p1.y()) * (p3.x() - p1.x())

fun convexHull(pts: List<Point>): List<Point> {
    val n = pts.size
    val minPt = pts.minByOrNull { it.second }!!
    val parts = pts.partition { it.y() == minPt.y() }
    val others =
        parts.second.sortedBy { (minPt.x().toDouble() - it.x().toDouble()) / (it.y().toDouble() - minPt.y().toDouble()) }
    val points = listOf(minPt) + parts.first.filter { it.x() - minPt.x() > 0 } +
            others + parts.first.filter { it.x() - minPt.x() < 0 }
    val stack = Stack<Point>()
    stack.push(points[0])
    stack.push(points[1])
    for (i in (2 until n)) {
        while (stack.size >= 2 &&
            ccw(stack.elementAt(stack.size - 2), stack.peek(), points[i]) <= 0
        ) {
            stack.pop()
        }
        stack.push(points[i])

    }
    return stack.toList()
}

interface PointDrawable {
    val default: String
    fun draw(): String
}

fun Map<Point, PointDrawable>.toLists(): List<MutableList<String>> {
    val maxWidth = this.keys.maxByOrNull { it.first }!!.first
    val minWidth = this.keys.minByOrNull { it.first }!!.first
    val maxHeight = this.keys.maxByOrNull { it.second }!!.second
    val minHeight = this.keys.minByOrNull { it.second }!!.second
    val default = this.values.first().default
    val wideArray = (minWidth..maxWidth).map { default }.toList()
    val printGrid = (minHeight..maxHeight).map { wideArray.toMutableList() }
    this.entries.filter { it.key != Point(-1, 0) }.forEach { (pt, t) ->
        printGrid[pt.y() - minHeight][pt.x() - minWidth] = t.draw()
    }
    return printGrid
}

fun Map<Point, PointDrawable>.draw(): String {
    return this.toLists()
        .joinToString(separator = "\n") { l -> l.joinToString(separator = "") { it } }
}

fun Point.getAngleNeighbors(input: List<List<Char>>): Set<Point> {
    val anglePoints = setOf(
        Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0),
        Point(1, 1), Point(1, -1), Point(-1, 1), Point(-1, -1)
    )
    val maxScale = maxOf(input.size, input.first().size)
    val neighbors = anglePoints.mapNotNull { pt ->
        (1..maxScale).map { this + pt * it }
            .firstOrNull {
                it.y() >= 0 && it.y() < input.size
                        && it.x() >= 0 && it.x() < input[it.y()].size
                        && setOf('#', 'L').contains(input[it.y()][it.x()])
            }
    }
    return neighbors.toSet()
}