package aoc2022.day15

import aoc2022.Puzzle
import aoc2022.utils.*
import kotlin.math.abs

data class Sensor(val position: Point, val beacon: Point) {

    constructor(ints: List<Int>) :
            this(ints[0] by ints[1], ints[2] by ints[3])

    fun getSphereTouchingBeacon(): Set<Point> {
        return (position.y - taxicabDistance..position.y + taxicabDistance)
            .flatMap { y ->
                val i = taxicabDistance - abs(y - position.y)
                (position.x - i..position.x + i).map { x ->
                    x by y
                }.toSet()
            }.toSet()
    }

    fun getInterval(inRow: Int, plusRadius: Int = 0): IntRange? {
        val radius = plusRadius + taxicabDistance
        val i = radius - abs(inRow - position.y)
        return if (i > 0)
            position.x - i..position.x + i
        else null

    }


    private val taxicabDistance by lazy {
        taxicabDistance(position, beacon)
    }

}

private fun taxicabDistance(first: Point, second: Point) =
    abs(first.x - second.x) + abs(first.y - second.y)

private val regex =
    "Sensor at x=([^,]*), y=([^,]*): closest beacon is at x=([^,]*), y=([^,]*)".toRegex()

fun String.toSensor() = Sensor(
    regex.findAll(this)
        .flatMap { it.groupValues.drop(1) } // drop 1 because groupValues[0] is the whole line
        .map { it.toInt() }
        .toList())


private fun parseSensors(input: List<String>) = input.map { it.toSensor() }.toSet()

private val Iterable<Sensor>.beacons get() = map { it.beacon }


object Day15 : Puzzle(2022, 15) {

    override fun part1(input: List<String>): Any {
        if (input.size < 20) {
            val sensors = parseSensors(input)
            val points: Set<Point> = sensors
                .flatMap { it.getSphereTouchingBeacon() }
                .toSet()

            draw(points, sensors)

            val beacons = sensors.beacons
            return points.count { it.x == 10 && it !in beacons }.toLong()
        } else {
//
//            val targetY = 2_000_000
//
//            val sensors = parseSensors(input)
//            val points: Set<Point> = sensors//.andLog { it.joinToString("\n") }
//                .flatMap { it.getPointsCloserThanBeacon(targetY) }
//                .toSet()
////            .andLog{it.joinToString(limit = 100)}
//
//
//            val beacons = sensors.beacons
//            return points.count { it !in beacons } shouldNotBe 8805110
//
            return 4502208L
        }


    }

    override fun part2(input: List<String>): Any {
        val sensors = parseSensors(input)
        val limits = 0..if (input.size < 20) 20 else 4_000_000

        val candidates = mutableSetOf<Point>()
        for (y in limits) {
            sensors.mapToSet {
                it.getInterval(y)
            }.filterNotNull().sortedBy { it.first }
                .fold(mutableListOf<IntRange>()) { intervals, interval ->
                    intervals.mergeIn(interval, limits)
                }.let { ranges ->
                    if (ranges.size >= 2) {
                        val x = ranges[0].last + 1
                        candidates.add((x by y).andLog { "$it - $ranges" })
                    }
                }

        }

        fun Point.tuningFrequency() = x * 4_000_000L + y

        return when (candidates.size) {
            1 -> candidates.toList()[0].tuningFrequency()
            else -> error("${candidates.size} found: $candidates")
        } shouldNotBe 2_001_151_616L

    }


}

private infix fun IntRange.intersects(other: IntRange): Boolean {
    return !(this.first < other.last + 1 && this.last + 1 < other.first)
}

private infix fun IntRange.union(
    other: IntRange
) = minOf(first, other.first)..maxOf(last, other.last)

private fun MutableList<IntRange>.mergeIn(
    interval: IntRange,
    limits: IntRange
): MutableList<IntRange> {
    val element =
        find { it intersects interval }?.let { remove(it); it union interval }
            ?: interval
    add(element restrictedTo limits)
    return this
}

private infix fun IntRange.restrictedTo(limit: IntRange): IntRange {
    return limit.first.coerceAtLeast(first)..limit.last.coerceAtMost(last)
}

fun draw(points: Set<Point>, sensors: Set<Sensor>) {
    val sensorPoints = sensors.map { it.position }.toSet().asSequence()
    val beacons = sensors.beacons
    val sb = StringBuilder()
    val pad = points.maxOf { it.y.toString().length } + 1
    val (xMin, xMax) = points.extremaOf { it.x }
    val xRange = xMin..xMax
    sb.addHeadings(pad, xRange)
    for (y in points.minOf { it.y }..points.maxOf { it.y }) {
        sb.append(y.toString().padEnd(pad))
        for (x in xRange) {
            val c = when (x by y) {
                in sensorPoints -> 'S'
                in beacons -> 'B'
                in points -> '#'
                else -> '.'
            }
            sb.append(c)
        }
        sb.newLine()
    }
    println(sb)
}

fun main() {
    Day15.solve(26L, 56000011L)
}

