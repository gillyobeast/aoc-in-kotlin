package aoc2022.utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput() = readFile("input.txt")
fun readInput(year: Int, day: Int) = readFile("$year/$day/input.txt")
fun readTestInput() = readFile("test_input.txt")
fun readTestInput(year: Int, day: Int) = readFile("$year/$day/test_input.txt")

private fun readFile(fileName: String) = File("src/main/resources", fileName)
    .readLines()

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

infix fun <L, T> ((L) -> T).appliedTo(
    input: L
): T {
    return this(input)
}

infix fun <T> T.returns(result: T) {
    check(this == result) { "expected $result but was $this" }
}

fun <T> T.shouldNotBe(equalTo: T): T {
    check(this != equalTo) { "Shouldn't be $equalTo" }
    return this
}

typealias Point2D<E> = Pair<E, E>
typealias Point = Point2D<Int>

