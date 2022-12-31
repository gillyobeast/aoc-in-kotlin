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

fun <E> E.andLog(extra: Any = ""): E {
    return also { println(extra.toString() + this) }
}

fun <E> List<E>.toPoint(): Point2D<E> {
    check(this.size == 2) { "Can only make a pair out of two values" }
    return this[0] to this[1]
}

infix fun <L, T> ((L) -> T).appliedTo(
    input: L
): T {
    return this(input)
}

infix fun <T> T.returns(result: T) {
    check(this == result) { "expected $result but was $this" }
}

infix fun <T> T.shouldNotBe(equalTo: T): T {
    check(this != equalTo) { "Shouldn't be $equalTo" }
    return this
}

typealias Point2D<E> = Pair<E, E>



data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x,$y)"
    }
}
infix fun Int.by(other: Int): Point {
    return Point(this, other)
}

fun List<Int>.toPoint(): Point {
    check(this.size == 2) { "Can only make a pair out of two values" }
    return Point(this[0], this[1])
}

fun Pair<Int, Int>.toPoint(): Point {
    return Point(first, second)
}
