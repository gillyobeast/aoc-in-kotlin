package aoc2022.day08

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Suppress("FunctionName", "kotlin:S100")
class Day08KtTest {

    @Test
    fun `viewDistanceTo works for various trees`() {
        assertEquals(1, 1.viewDistanceTo(listOf(1, 2, 3, 4)))
        assertEquals(4, 4.viewDistanceTo(listOf(1, 2, 3, 4)))
        assertEquals(3, 3.viewDistanceTo(listOf(1, 2, 3, 4, 5)))
        assertEquals(4, 4.viewDistanceTo(listOf(1, 2, 3, 4, 5)))
        assertEquals(1, 1.viewDistanceTo(listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun `viewDistanceTo works for long row`() {
        val trees: List<Int> =
            "20311003030020403303010231542401043011111536600504555130543502251500155511123400111142111343321103"
                .split("").filter(String::isNotEmpty).map(String::toInt)
        assertEquals(1, 0.viewDistanceTo(trees))
        assertEquals(1, 1.viewDistanceTo(trees))
        assertEquals(1, 2.viewDistanceTo(trees))
        assertEquals(3, 3.viewDistanceTo(trees))
        assertEquals(15, 4.viewDistanceTo(trees))
        assertEquals(27, 5.viewDistanceTo(trees))
        assertEquals(44, 6.viewDistanceTo(trees))
        assertEquals(98, 7.viewDistanceTo(trees))
        assertEquals(98, 8.viewDistanceTo(trees))
        assertEquals(98, 9.viewDistanceTo(trees))
    }
}
