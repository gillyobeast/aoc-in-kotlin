package aoc2022.day07

import aoc2022.utils.appliedTo
import aoc2022.utils.readInput
import aoc2022.utils.readTestInput


/// strategy:
//  add `/` to tree
//  dirname = "/"
//  split input by lines starting $
//  for each of those:
//      if `cd ..`, set dirname to 'parent'
//      if `cd dirname` set `currentDir` to `dirname`
//      if `ls`, iterate over remaining lines:
//          if starts with `\d+`, add child with that size and name that follows
//          else if starts with `dir dirname`, add child with name `dirname`

//  then traverse the tree, grabbing all `dir`s with size < limit and adding to list:
//  list.sumOf { it.size }

private val whitespace = "\\s".toRegex()

private val newline = "\\n".toRegex()

private fun buildFs(input: List<String>): FsObject {
    val tree: FsObject = Directory("/")
    var currentDirectory: FsObject = tree

    input.joinToString("\n")
        .split("\\\$".toRegex())
//        .also(::println)
        .drop(2) // as we're already in root
        .forEach { it ->
            val lines = it.split(newline)
            val argLine = lines.first()
            val args = argLine.split(whitespace)
            if (args[1] == "cd") {
                val dirname = args[2]
                currentDirectory =
                    if (dirname == "..") {
                        currentDirectory.parent!!
                    } else {
                        currentDirectory.getOrAddDir(dirname)
                    }
            } else { // ls
                lines.drop(1) // ignore the ls
                    .filter(String::isNotBlank).forEach {
                        val response = it.split(whitespace)
                        if (response[0] == "dir") {
                            currentDirectory.addChild(Directory(response[1]))
                        } else { // file
                            val child = File(response[1], response[0].toInt())
                            currentDirectory.addChild(child)
                        }
                    }

            }
        }
    return tree
}

fun part1(input: List<String>): Int {

    return buildFs(input)
        .directoriesInRange(1..100_000)
        .sumOf { it.size }
}

// need directory x s.t. x.size is minimised and
//      tree.size - x.size < 70_000_000 - 30_000_000 = 40_000_000
fun part2(input: List<String>): Int {

    val fileSystem = buildFs(input)
    println(fileSystem.toString())

    return fileSystem
        .allDirectories()
        .filter { fileSystem.size - it.size < 40_000_000 }
        .minOf { it.size }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput()
    val input = readInput()

    // part 1
    ::part1.appliedTo(testInput, returns = 95437)
    val part1 = part1(input)
    println("Part 1: $part1")
    check(part1 != 2127288) { "Shouldn't be 2127288!" }

    // part 2
    ::part2.appliedTo(testInput, returns = 24933642)
    val part2 = part2(input)
    println("Part 2: $part2")
    check(part2 != 14694309) { "Shouldn't be 14694309!" }
}

class File(name: String) : FsObject(name) {
    constructor(name: String, size: Int) : this(name) {
        this.size = size
    }

    override fun type(): String = "file"
}

class Directory(name: String) : FsObject(name) {
    override fun type(): String = "dir"

}


sealed class FsObject(private val name: String) {
    var parent: FsObject? = null

    val children: MutableList<FsObject> = mutableListOf()

    var size: Int = 0
        get() {
            return if (this is File) field
            else getChildFiles().sumOf { it.size }
        }

    fun addChild(child: FsObject): FsObject {
        children.add(child)
        child.parent = this
        return child
    }

    private fun getChildFiles(): List<File> {
        if (this is File) return listOf(this)
        val (files, dirs) = children.partition { it is File }
        val children = mutableListOf<File>()
        children.addAll(files.map { it as File })
        children.addAll(dirs.flatMap { it.getChildFiles() })
        return children.toList()
    }

    private fun depth(): Int {
        var parent = this.parent
        var depth = 0
        while (parent != null) {
            depth++
            parent = parent.parent
        }
        return depth
    }

    override fun toString(): String {
        var name =
            "\t".repeat(depth()) +
                    (if (this is File) "-" else "\\") + " " +
                    name + " (size = " + size + ", " + type() + ")"
        if (children.isNotEmpty()) {
            name += children.joinToString("") { "\n$it" }
        }
        return name
    }

    abstract fun type(): String

    fun getOrAddDir(name: String): FsObject {
        return children.find { it.name == name } ?: addChild(Directory(name))
    }

    fun directoriesInRange(intRange: IntRange): List<FsObject> {
        return allDirectories().filter { it.size in intRange }
    }

    fun allDirectories(): List<FsObject> {
        if (this is File) return emptyList()

        val directories = mutableListOf(this)
        directories.addAll(children
            .filterIsInstance<Directory>()
            .flatMap {
                it.allDirectories()
            })
        return directories
    }

}
