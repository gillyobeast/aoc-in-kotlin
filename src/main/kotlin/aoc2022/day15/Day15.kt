package aoc2022.day15

import aoc2022.Puzzle
import aoc2022.utils.Point
import aoc2022.utils.andLog
import aoc2022.utils.by
import aoc2022.utils.shouldNotBe
import kotlin.math.abs

data class Sensor(val position: Point, val beacon: Point) {

    constructor(ints: List<Int>) :
            this(ints[0] by ints[1], ints[2] by ints[3])

    fun getPointsCloserThanBeacon(targetY: Int): Set<Point> {
        return (position.y - taxicabDistance..position.y + taxicabDistance)
            .flatMap { y ->
                if (y == targetY) {
                    val i = taxicabDistance - abs(y - position.y)
                    (position.x - i..position.x + i).map { x ->
                        x by y
                    }.toSet()
                } else emptySet()
            }.toSet()
    }

    private val taxicabDistance by lazy {
        abs(position.x - beacon.x) + abs(position.y - beacon.y)
    }

}

private val regex =
    "Sensor at x=([^,]*), y=([^,]*): closest beacon is at x=([^,]*), y=([^,]*)".toRegex()

fun String.toSensor() = Sensor(
    regex.findAll(this)
        .flatMap { it.groupValues.drop(1) } // drop 1 because groupValues[0] is the whole line
        .map { it.toInt() }
        .toList())


private fun parseSensors(input: List<String>) = input.map { it.toSensor() }.asSequence()

private val Sequence<Sensor>.beacons get() = map { it.beacon }


object Day15 : Puzzle(2022, 15) {

    override fun part1(input: List<String>): Any {

        val targetY = if (input.size < 20) 10 else 2_000_000

        val sensors = parseSensors(input)
        val points: Set<Point> = sensors//.andLog { it.joinToString("\n") }
            .flatMap { it.getPointsCloserThanBeacon(targetY) }
            .toSet()
//            .andLog{it.joinToString(limit = 100)}


        val beacons = sensors.beacons
        return points.count { it !in beacons } shouldNotBe 8805110

    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

//fun draw(points: Sequence<Point>, sensors: Set<Sensor>) {
//    val sensorPoints = sensors.map { it.position }.toSet().asSequence()
//    val beacons = sensors.map { it.beacon }.asSequence()
//    val sb = StringBuilder()
//    val pad = points.maxOf { it.y.toString().length } + 1
//    val (xMin, xMax) = points.extremaOf { it.x }
//    val xRange = xMin..xMax
//    sb.addHeadings(pad, xRange)
//    for (y in points.minOf { it.y }..points.maxOf { it.y }) {
//        sb.append(y.toString().padEnd(pad))
//        for (x in xRange) {
//            val c = when (x by y) {
//                in sensorPoints -> 'S'
//                in beacons -> 'B'
//                in points -> '#'
//                else -> '.'
//            }
//            sb.append(c)
//        }
//        sb.newLine()
//    }
//    println(sb)
//}

fun main() {
    Day15.solve(26, 56000011)
}

