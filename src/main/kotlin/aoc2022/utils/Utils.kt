package aoc2022.utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput() = readFile("input.txt")
fun readTestInput() = readFile("test_input.txt")

private fun readFile(fileName: String) = File("src/main/resources", fileName)
    .readLines()

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <L, T> ((L) -> T).appliedTo(
    input: L,
    returns: T
) {
    val output = this(input)
    check(output == returns) { "expected $returns but was $output" }
}

fun <T> T.shouldNotBe(equalTo: T): T {
    check(this != equalTo) { "Shouldn't be $equalTo" }
    return this
}

typealias Point2D<E> = Pair<E, E>
typealias Point = Point2D<Int>

