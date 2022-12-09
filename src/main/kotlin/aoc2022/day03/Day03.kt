package aoc2022.day03

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

private fun <E> List<E>.toTriple(): Triple<E, E, E> {
    assert(this.size == 3)
    return Triple(this[0], this[1], this[2])
}

fun main() {

    val lowerPriorities =
        ("abcdefghijklmnopqrstuvwxyz".toCharArray()).zip((1..26).toList()).toMap()
    val upperPriorities =
        ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()).zip((27..52).toList()).toMap()

    fun splitBackpack(backpack: String): Pair<String, String> {
        val n = backpack.length / 2
        return backpack.substring(0, n) to backpack.substring(n, backpack.length)
    }

    fun findCommonItem(backPack: Pair<String, String>): Char {
        for (i in backPack.first) {
            if (backPack.second.contains(i.toString().toRegex())) {
                return i
            }
        }
        throw IllegalStateException("No common character found between halves of [$backPack]!")
    }

    fun findCommonItem(backPacks: Triple<String, String, String>): Char {
        for (i in backPacks.first) {
            val regex = i.toString().toRegex()
            if (backPacks.second.contains(regex) && backPacks.third.contains(regex)) {
                return i
            }
        }
        throw IllegalStateException("No common character found between three parts of [$backPacks]!")
    }

    fun toPriority(c: Char): Int {
        return lowerPriorities[c] ?: upperPriorities[c]
        ?: throw IllegalStateException("No priority found for [$c]!")
    }

    fun part1(input: List<String>): Int {
        return input
            .map(::splitBackpack)
            .map(::findCommonItem)
            .map(::toPriority)
            .sum()

    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .chunked(3)
            .map { it.toTriple() }
            .map(::findCommonItem)
            .map(::toPriority)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput()
    val input = readInput()

    // part 1
    ::part1.appliedTo(testInput, returns = 157)
    println("Part 1: ${part1(input)}")

    // part 2
    ::part2.appliedTo(testInput, returns = 70)
    println("Part 2: ${part2(input)}")
}

