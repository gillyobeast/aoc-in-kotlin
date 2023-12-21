package aoc2022.day03

import Puzzle

object Day03 : Puzzle(2022, 3) {
    private fun <E> List<E>.toTriple(): Triple<E, E, E> {
        assert(this.size == 3)
        return Triple(this[0], this[1], this[2])
    }

    private val lowerPriorities =
        ("abcdefghijklmnopqrstuvwxyz".toCharArray()).zip((1..26).toList()).toMap()
    private val upperPriorities =
        ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()).zip((27..52).toList()).toMap()

    private fun splitBackpack(backpack: String): Pair<String, String> {
        val n = backpack.length / 2
        return backpack.substring(0, n) to backpack.substring(n, backpack.length)
    }

    private fun findCommonItem(backPack: Pair<String, String>): Char {
        for (i in backPack.first) {
            if (backPack.second.contains(i.toString().toRegex())) {
                return i
            }
        }
        throw IllegalStateException("No common character found between halves of [$backPack]!")
    }

    private fun findCommonItem(backPacks: Triple<String, String, String>): Char {
        for (i in backPacks.first) {
            val regex = i.toString().toRegex()
            if (backPacks.second.contains(regex) && backPacks.third.contains(regex)) {
                return i
            }
        }
        throw IllegalStateException("No common character found between three parts of [$backPacks]!")
    }

    private fun toPriority(c: Char): Int {
        return lowerPriorities[c] ?: upperPriorities[c]
        ?: throw IllegalStateException("No priority found for [$c]!")
    }

    override fun part1(input: List<String>): Int {
        return input
            .map(::splitBackpack)
            .map(::findCommonItem)
            .map(::toPriority)
            .sum()

    }

    override fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .chunked(3)
            .map { it.toTriple() }
            .map(::findCommonItem)
            .map(::toPriority)
            .sum()
    }

}

fun main() = Day03.solve(157, 70)

