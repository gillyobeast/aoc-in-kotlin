package aoc2022.utils

typealias Matrix<E> = List<List<E>>

fun <E> Matrix<E>.prettyPrint() {
    println(map { it.joinToString(" ") }.joinToString("\n"))
}

fun <E> Matrix<E>.prettyPrint(path: List<Pair<Int, Int>>) {
    println(mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, value ->
            (if (path.contains(rowIndex to colIndex)) ">" else " ") + value.toString()
        }.joinToString("")
    }.joinToString("\n"))
}

fun matrixOf(input: List<String>): Matrix<Int> = input.map {
    it.split("").filter(String::isNotBlank).map(String::toInt).toList()
}

fun <E> Matrix<E>.transposed(): Matrix<E> {
    forEach { check(it.size == this[0].size) { "Non-square matrix passed to transpose" } }
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

operator fun <E> Matrix<E>.get(
    rowIndex: Int,
    colIndex: Int
): Triple<List<E>, List<E>, E> {
    val row = this[rowIndex]
    val column = column(colIndex)
    val tree = row[colIndex]
    return Triple(row, column, tree)
}


fun <E> Matrix<E>.iterate(block: Matrix<E>.(Int, Int, E) -> Unit) {
    indices.forEach { rowIndex ->
        this[rowIndex].forEachIndexed { colIndex, it ->
            this.block(
                rowIndex, colIndex, it
            )
        }
    }
}

fun <E> Matrix<E>.column(i: Int): List<E> {
    return map { it[i] }.toList()
}


fun List<Int>.beforeAndAfter(index: Int): Pair<List<Int>, List<Int>> {
    return subList(0, index) to subList(index + 1, size)
}
