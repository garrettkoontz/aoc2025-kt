package com.k00ntz.aoc2025.util

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.stream.Collectors
import kotlin.system.measureTimeMillis

fun measureAndPrintTime(block: () -> Unit) {
    val time = measureTimeMillis(block)
    println(" took $time ms")
}

fun Pair<Int, Int>.sum() =
    this.first + this.second


fun List<CharArray>.getPoint(pt: Point): Char =
    this[pt.y()][pt.x()]

fun <T> List<List<T>>.getPoint(pt: Point): T =
    this[pt.y()][pt.x()]

fun List<CharArray>.getPointOrNull(pt: Point): Char? {
    if (pt.y() < 0 || pt.y() >= this.size) return null
    if (pt.x() < 0 || pt.x() >= this[pt.y()].size) return null
    return this[pt.y()][pt.x()]
}

@JvmName("getPointOrNullChar")
fun List<List<Char>>.getPointOrNull(pt: Point): Char? {
    if (pt.y() < 0 || pt.y() >= this.size) return null
    if (pt.x() < 0 || pt.x() >= this[pt.y()].size) return null
    return this[pt.y()][pt.x()]
}

inline fun <T : Any> parseFile(fileName: String, crossinline parsefn: (String) -> T): List<T> =
    ClassLoader.getSystemResourceAsStream(fileName).use { inputStream ->
        if (inputStream == null) throw RuntimeException("resource $fileName not found")
        inputStream.bufferedReader().lines().map { parsefn(it) }.collect(Collectors.toList())
    }

fun getFile(fileName: String): List<String> =
    ClassLoader.getSystemResourceAsStream(fileName).use { inputStream ->
        if (inputStream == null) throw RuntimeException("resource $fileName not found")
        inputStream.bufferedReader().lines().collect(Collectors.toList())
    }

inline fun <T : Any> parseFileIndexed(fileName: String, crossinline parsefn: (Int, String) -> T): List<T> =
    ClassLoader.getSystemResourceAsStream(fileName).use { inputStream ->
        if (inputStream == null) throw RuntimeException("resource $fileName not found")
        inputStream.bufferedReader().lines().collect(Collectors.toList()).mapIndexed(parsefn).toList()
    }

inline fun <T : Any> parseLine(fileName: String, crossinline parsefn: (String) -> T): T =
    ClassLoader.getSystemResourceAsStream(fileName).use { inputStream ->
        if (inputStream == null) throw RuntimeException("resource $fileName not found")
        inputStream.bufferedReader().lines().map { parsefn(it) }.findFirst()
    }.orElseThrow { RuntimeException("Nothing in $fileName") }

fun parseLineForNumbers(fileName: String): List<Int> =
    parseLine(fileName) { s -> s.split(",").map { it.toInt() } }

fun groupSeparatedByEmpty(strs: List<String>): List<List<String>> =
    strs.fold(mutableListOf(mutableListOf())) { acc: MutableList<MutableList<String>>, s: String? ->
        if (s.isNullOrBlank()) {
            acc.add(mutableListOf())
        } else {
            acc[acc.size - 1].add(s)
        }
        acc
    }

fun <T> cartesian(c1: Iterable<T>, c2: Iterable<T> = c1): List<Pair<T, T>> =
    c1.flatMap { a -> c2.map { b -> Pair(a, b) } }

fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}

fun <A, B> Iterator<A>.pmap(f: suspend (A) -> B): List<B> {
    val sup = this
    return object : Iterable<A> {
        override fun iterator(): Iterator<A> {
            return sup
        }

    }.pmap(f)
}

fun <T, R> Array<T>.pmap(f: suspend (T) -> R) = runBlocking {
    map { async { f(it) } }.map { it.await() }
}

fun <A, B> Iterable<A>.pMapIndexed(f: suspend (Int, A) -> B): List<B> = runBlocking {
    mapIndexed { index, i -> async { f(index, i) } }.map { it.await() }
}


fun <T, R> Array<out T>.pMapIndexed(f: (index: Int, T) -> R): List<R> = runBlocking {
    mapIndexed { index, i -> async { f(index, i) } }.map { it.await() }
}

fun gcd(number1: Long, number2: Long): Long {
    return if (number2 == 0L) {
        number1
    } else gcd(number2, number1 % number2)
}

fun lcm(number1: Long, number2: Long): Long =
    (number1 / gcd(number1, number2)) * number2

fun twoSum(sorted: List<Int>, targetValue: Int): Pair<Int, Int> {
    var i = 0
    var j = sorted.size - 1
    while (i != j) {
        val sum = sorted[i] + sorted[j]
        if (sum == targetValue) return Pair(sorted[i], sorted[j])
        if (sum < targetValue) ++i
        if (sum > targetValue) --j
    }
    return Pair(0, 0)
}

fun twoSum(sorted: List<Long>, targetValue: Long): Pair<Long, Long> {
    var i = 0
    var j = sorted.size - 1
    while (i != j) {
        val sum = sorted[i] + sorted[j]
        if (sum == targetValue) return Pair(sorted[i], sorted[j])
        if (sum < targetValue) ++i
        if (sum > targetValue) --j
    }
    return Pair(0L, 0L)
}

inline fun <T> Iterable<T>.firstOrNullIndexed(operation: (index: Int, T) -> Boolean): T? {
    var index = 0
    for (element in this) if (operation(index++, element)) return element
    return null
}

fun findGCD(number1: Long, number2: Long): Long =
    if (number2 == 0L) {
        number1
    } else findGCD(number2, number1 % number2)

fun findLCM(a: Long, b: Long): Long =
    a * b / findGCD(a, b)

fun <T> List<T>.ringIndexOf(t: T): Int =
    ringIndex(indexOf(t))

fun <T> List<T>.ringIndex(i: Int): Int =
    (i + size) % size