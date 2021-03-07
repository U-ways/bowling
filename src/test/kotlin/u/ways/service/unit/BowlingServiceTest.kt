package u.ways.service.unit

import u.ways.domain.Frame
import u.ways.domain.Player
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import u.ways.service.BowlingService

internal class BowlingServiceTest {

    @Test
    fun addPointsToTheFirstBall() {
        val player = mockk<Player>()
        val bs = BowlingService(listOf(player))

        every { player.frames } returns mutableListOf()

        `when` { bs.addPoints(10) }

        val firstBall = player.frames.first().first
        assertEquals(firstBall, 10)
    }

    @Test
    fun addPointsToTheSecondBall() {
        val player = mockk<Player>()
        val bs = BowlingService(listOf(player))

        every { player.frames } returns mutableListOf()

        `when` {
            bs.addPoints(5)
            bs.addPoints(5)
        }

        val secondBall = player.frames.first().second
        assertEquals(secondBall, 5)
    }

    @Test
    fun addPointsToTheThirdBall() {
        val player = mockk<Player>()
        val bs = BowlingService(listOf(player))

        every { player.frames } returns MutableList(9) { Frame() }

        `when` {
            bs.addPoints(0)
            bs.addPoints(10)
            bs.addPoints(5)
        }

        val thirdBall = player.frames.last().third
        assertEquals(thirdBall, 5)
    }

    private fun <T> `when`(`fun`: () -> T): T = `fun`()
}