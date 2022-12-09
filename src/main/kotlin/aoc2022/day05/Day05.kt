package aoc2022.day05

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import java.util.*

fun main() {

    val whitespace = "\\s".toRegex()

    fun List<String>.extractIndices(): List<String> {
        return last().chunked(4).filter(String::isNotBlank)
    }

    fun parse(input: List<String>): Pair<List<String>, List<String>> = input
        .filter(String::isNotEmpty)
        .partition { it.startsWith("move") }

    fun String.extractLabel(): Char {
        return if (this.length > 1) this[1] else this[0]
    }

    fun String.stripSpaces(): String = replace(whitespace, "")

    fun buildStacks(stackData: List<String>): List<Stack<Char>> {
        val numberOfStacks = stackData.extractIndices().last().stripSpaces()

        val stacks = mutableListOf<Stack<Char>>()
        repeat(numberOfStacks.toInt()) {
            stacks.add(Stack())
        }

        stackData.dropLast(1).forEach {
            it.chunked(4).forEachIndexed { idx, item ->
                if (item.isNotBlank()) {
                    stacks[idx].push(item.extractLabel())
                }
            }
        }
        return stacks.onEach { it.reverse() }.toList()
    }

    fun String.triple(stacks: List<Stack<Char>>): Triple<Int, Stack<Char>, Stack<Char>> {
        val split = this.split(whitespace)
        val count = split[1].toInt()
        val source = stacks[split[3].toInt() - 1]
        val target = stacks[split[5].toInt() - 1]
        return Triple(count, source, target)
    }

    fun String.applyTo(stacks: List<Stack<Char>>) {
        val (count, source, target) = triple(stacks)

        repeat(count) {
            target.push(source.pop())
        }
    }

    fun String.applyToPart2(stacks: List<Stack<Char>>) {
        val (count, source, target) = triple(stacks)

        val temp = Stack<Char>()
        repeat(count) {
            temp.push(source.pop())
        }
        repeat(count) {
            target.push(temp.pop())
        }
    }

    fun List<String>.applyTo(stacks: List<Stack<Char>>) {
        forEach { it.applyTo(stacks) }
    }

    fun List<String>.applyToPart2(stacks: List<Stack<Char>>) {
        forEach { it.applyToPart2(stacks) }
    }

    fun part1(input: List<String>): String {

        val (instructions, stackData) = parse(input)

        val stacks = buildStacks(stackData)

        instructions.applyTo(stacks)


        return stacks.map { it.pop() }.joinToString("")
    }

    fun part2(input: List<String>): String {

        val (instructions, stackData) = parse(input)

        val stacks = buildStacks(stackData)

        instructions.applyToPart2(stacks)

        return stacks.map { it.pop() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput()
    val input = readInput()

    // part 1
    ::part1.appliedTo(testInput, returns = "CMZ")
    println("Part 1: ${part1(input)}")

    // part 2
    ::part2.appliedTo(testInput, returns = "MCD")
    println("Part 2: ${part2(input)}")
}




