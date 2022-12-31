package aoc2022.day15

import aoc2022.Puzzle
import aoc2022.utils.Point
import aoc2022.utils.andLog
import aoc2022.utils.by

data class Sensor(val position: Point, val beacon: Point) {
    constructor(x: Int, y: Int, beaconX: Int, beaconY: Int) :
            this(x by y, beaconX by beaconY)

    constructor(ints: List<Int>) :
            this(ints[0], ints[1], ints[2], ints[3])

}

private val regex =
    "Sensor at x=([^,]*), y=([^,]*): closest beacon is at x=([^,]*), y=([^,]*)".toRegex()

fun String.toSensor() = Sensor(
    regex.findAll(this)
        .flatMap { it.groupValues.drop(1) } // drop 1 because groupValues[0] is the whole line
        .map { it.toInt() }
        .toList())


private fun parseSensors(input: List<String>) = input.map { it.toSensor() }.toSet()

object Day15 : Puzzle(2022, 15) {

    override fun part1(input: List<String>): Any {

        val sensors: Set<Sensor> = parseSensors(input)
            .andLog()



        TODO("Not yet implemented")
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }

}

fun main() {
    Day15.solve(26, -1)
}

