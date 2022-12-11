package aoc2022

import aoc2022.day10.Day10
import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput

abstract class Puzzle(val year: Int, val day:Int){
    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any
    open fun <T> solve(part1TestResult: T, part2TestResult: T) {
        val testInput = readTestInput()
        val input = readInput()

        // part 1
        ::part1.appliedTo(testInput, returns = part1TestResult)
        println("Part 1: ${Day10.part1(input)}")

        // part 1
        ::part1.appliedTo(testInput, returns = part2TestResult)
        println("Part 1: ${Day10.part1(input)}")

    }
}

fun main(){
    
}
