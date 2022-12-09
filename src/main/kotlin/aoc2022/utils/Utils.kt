package aoc2022.utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput() = File("src/main/resources", "input.txt")
    .readLines()

/**
 * Converts string to aoc2022.utils.md5 hash.
 */
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


typealias Matrix<E> = List<List<E>>
typealias Point2D<E> = Pair<E, E>
typealias DiscretePoint = Point2D<Int>

fun Matrix<Int>.prettyPrint() {
    println(joinToString("\n"))
}

fun matrixOf(input: List<String>) = input.map {
    it.split("").filter(String::isNotBlank).map(String::toInt).toList()
}


fun <T> T.shouldNotBe(equalTo: T): T {
    check(this != equalTo) { "Shouldn't be $equalTo" }
    return this
}

fun <E> Matrix<E>.transposed(): Matrix<E> {
    checkSquare()
    val copy = mutableListOf<List<E>>()
    for (i: Int in 0..this.lastIndex) {
        val list = mutableListOf<E>()
        for (j: Int in 0..this[i].lastIndex) {
            list.add(j, this[j][i])
        }
        copy.add(list.toList())
    }

    return copy.toList()
}

private fun <E> Matrix<E>.checkSquare() {
    forEach { check(it.size == this[0].size) { "Non-square matrix passed to transpose" } }
}


operator fun <E> Matrix<E>.get(
    rowIndex: Int,
    colIndex: Int
): Triple<List<E>, List<E>, E> {
    val row = this[rowIndex]
    val column = column(colIndex)
    val tree = row[colIndex]
    return Triple(row, column, tree)
}

fun List<Int>.beforeAndAfter(index: Int): Pair<List<Int>, List<Int>> {
    return subList(0, index) to subList(index + 1, size)
}

fun <E> Matrix<E>.column(i: Int): List<E> {
    return map { it[i] }.toList()
}
