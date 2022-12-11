package aoc2022.day05

import aoc2022.Puzzle
import java.util.*

object Day05 : Puzzle(2022, 5) {

    private val whitespace = "\\s".toRegex()

    private fun List<String>.extractIndices(): List<String> {
        return last().chunked(4).filter(String::isNotBlank)
    }

    private fun parse(input: List<String>): Pair<List<String>, List<String>> = input
        .filter(String::isNotEmpty)
        .partition { it.startsWith("move") }

    private fun String.extractLabel(): Char {
        return if (this.length > 1) this[1] else this[0]
    }

    private fun String.stripSpaces(): String = replace(whitespace, "")

    private fun buildStacks(stackData: List<String>): List<Stack<Char>> {
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

    private fun String.triple(stacks: List<Stack<Char>>): Triple<Int, Stack<Char>, Stack<Char>> {
        val split = this.split(whitespace)
        val count = split[1].toInt()
        val source = stacks[split[3].toInt() - 1]
        val target = stacks[split[5].toInt() - 1]
        return Triple(count, source, target)
    }

    private fun String.applyTo(stacks: List<Stack<Char>>) {
        val (count, source, target) = triple(stacks)

        repeat(count) {
            target.push(source.pop())
        }
    }

    private fun String.applyToPart2(stacks: List<Stack<Char>>) {
        val (count, source, target) = triple(stacks)

        val temp = Stack<Char>()
        repeat(count) {
            temp.push(source.pop())
        }
        repeat(count) {
            target.push(temp.pop())
        }
    }

    private fun List<String>.applyTo(stacks: List<Stack<Char>>) {
        forEach { it.applyTo(stacks) }
    }

    private fun List<String>.applyToPart2(stacks: List<Stack<Char>>) {
        forEach { it.applyToPart2(stacks) }
    }

    override fun part1(input: List<String>): String {

        val (instructions, stackData) = parse(input)

        val stacks = buildStacks(stackData)

        instructions.applyTo(stacks)


        return stacks.map { it.pop() }.joinToString("")
    }

    override fun part2(input: List<String>): String {

        val (instructions, stackData) = parse(input)

        val stacks = buildStacks(stackData)

        instructions.applyToPart2(stacks)

        return stacks.map { it.pop() }.joinToString("")
    }

}

fun main() = Day05.solve("CMZ", "MCD")
