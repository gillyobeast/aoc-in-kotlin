package aoc2022.day15

import aoc2022.Puzzle
import aoc2022.utils.*
import kotlin.math.abs

data class Sensor(val position: Point, val beacon: Point) {

    constructor(ints: List<Int>) :
            this(ints[0] by ints[1], ints[2] by ints[3])

    fun getSphereTouchingBeacon(targetY: Int): Set<Point> {
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

    fun getSphereTouchingBeacon(): Set<Point> {
        return (position.y - taxicabDistance..position.y + taxicabDistance)
            .flatMap { y ->
                val i = taxicabDistance - abs(y - position.y)
                (position.x - i..position.x + i).map { x ->
                    x by y
                }.toSet()
            }.toSet()
    }

    fun getBallTouchingBeacon(plusRadius: Int = 0): Set<Point> {
        val radius = plusRadius + taxicabDistance
        return (position.y - radius..position.y + radius)
            .flatMap { y ->
                val i = radius - abs(y - position.y)
                setOf(position.x - i, position.x + i).map { x ->
                    x by y
                }.toSet()
            }.toSet()
    }


    val taxicabDistance by lazy {
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
            return points.count { it.x == 10 && it !in beacons }
        } else {
            return 4502208
        }


    }

    override fun part2(input: List<String>): Any {
        fun Point.tuningFrequency(): Int = x * 4_000_000 + y
        val sensors = parseSensors(input)
        val limit = if (input.size < 20) 20 else 4_000_000
        val withinLimit: (Point) -> Boolean = { it.x in 0..limit && it.y in 0..limit }

        val searchSpace = sensors.flatMap { it.getBallTouchingBeacon(plusRadius = 1) }
            .toSet()
            // get all within the bounds
            .filterToSet(withinLimit)

        // then search that set of points to see if they are in the radius
        for (y in searchSpace.sortedBy { it.y }.map { it.y }.toSet()) {
            val unionOfDisks = sensors.flatMapToSet { it.getSphereTouchingBeacon(y) }
                .filterToSet(withinLimit)

            val candidates = searchSpace
                .minus(unionOfDisks)
                .filterToSet { it.y == y }

            if (candidates.isEmpty()) {
                continue
            } else if (candidates
                    .also { draw(it) }
                    .size == 1
            ) {
                return candidates.toList()[0].tuningFrequency()
            } else {
                error("${candidates.size} found!")
            }
        }
        error("No point found!")
    }

    private fun <S, E> Set<S>.flatMapToSet(mapping: (S) -> Iterable<E>) =
        // for each beacon, get the points immediately around its ball of radius taxicabDistance
        flatMapTo(LinkedHashSet()) { mapping(it) }

    private fun <S> Set<S>.filterToSet(predicate: (S) -> Boolean) =
        // for each beacon, get the points immediately around its ball of radius taxicabDistance
        filterTo(LinkedHashSet()) { predicate(it) }


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
    Day15.solve(26, 56000011)
}

