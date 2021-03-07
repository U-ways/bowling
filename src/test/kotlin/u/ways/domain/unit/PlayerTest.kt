package u.ways.domain.unit

import u.ways.domain.Frame
import u.ways.domain.Player
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PlayerTest {
    @Test
    fun playerCanCalculateTotalScoreOfAllFramesPlayed() {
        val frames = mutableListOf<Frame>(
                Frame(5,5, bonus = 10),
                Frame(10,0, bonus = 6),
                Frame(3,3),
                Frame(0,5)
            )

        val player = Player("Ron", frames)
        val expected = frames.sumBy { it.calculateScore() }

        assertEquals(expected, player.calculateTotalScore())
    }
}