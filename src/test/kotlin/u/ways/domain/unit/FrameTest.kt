package u.ways.domain.unit

import u.ways.domain.Frame
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class FrameTest {
    private lateinit var frame: Frame

    @BeforeEach
    internal fun setUp() {
        frame = Frame()
    }

    @Test
    fun isStrikeWhenAllPinsAreDroppedFirstBall() {
        frame.first = 10
        assertTrue(frame.isStrike())
    }

    @Test
    fun isSpareWhenAllPinsAreKnockedDownWithinTwoBalls() {
        frame.first = 5
        frame.second = 5
        assertTrue(frame.isSpare())
    }
    @Test
    fun isSpareWhenSecondBallIs10() {
        frame.first = 0
        frame.second = 10
        assertTrue(frame.isSpare())
    }

    @Test
    fun calculateScore() {
        frame.first = 10
        frame.second = 10
        frame.third = 10
        frame.bonus = 5

        val expected = frame.first + frame.second + frame.third + frame.bonus
        val actual = frame.calculateScore()

        assertEquals(expected, actual)
    }
}