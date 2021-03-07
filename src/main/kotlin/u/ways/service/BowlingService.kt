package u.ways.service

import u.ways.domain.Ball
import u.ways.domain.Ball.*
import u.ways.domain.Frame
import u.ways.domain.Player

class BowlingService(val players: List<Player>) {
    var currentPlayer: Player = players.first()
    var currentFrame: Frame = Frame()
    var currentBall: Ball = First

    fun gameOver(): Boolean = players.last().frames.size == 10
    fun addPoints(points: Int) {
        check(!gameOver())

        if (currentPlayer.frames.size == 9) {
            return finalFrameCalculations(points)
        }

        when (currentBall) {
            First -> {
                currentFrame.first = currentFrame.first addleTen points
                if (currentFrame.isStrike()) return addBonusAndMoveToTheNextPlayer()
            }
            Second -> {
                currentFrame.second = currentFrame.second addleTenTotal points
                return addBonusAndMoveToTheNextPlayer()
            }
            else -> error("error: non-final frames should not allow for a third ball")
        }

        currentBall = values()[currentBall.ordinal + 1]
    }

    private fun finalFrameCalculations(points: Int) {
        when (currentBall) {
            First -> {
                currentFrame.first = currentFrame.first addleTen points
            }
            Second -> {
                if (currentFrame.isStrike()) currentFrame.second = currentFrame.second addleTen points
                else currentFrame.second = currentFrame.second addleTenTotal points

                if (!currentFrame.isStrike() && !currentFrame.isSpare())
                    return addBonusAndMoveToTheNextPlayer()
            }
            Third -> {
                if (currentFrame.second == 10) currentFrame.third = currentFrame.third addleTen points
                else currentFrame.third = currentFrame.third addleTenTotalThird points

                return addBonusAndMoveToTheNextPlayer()
            }
        }

        currentBall = values()[currentBall.ordinal + 1]
    }

    private fun addBonusAndMoveToTheNextPlayer() {
        if (currentPlayer.frames.isNotEmpty()) {
            val previousFrame = currentPlayer.frames.last()

            if (previousFrame.isStrike() && currentPlayer.frames.indexOf(previousFrame) == 8) {
                previousFrame.bonus = currentFrame.first + currentFrame.second
            } else if (previousFrame.isSpare()) {
                previousFrame.bonus = currentFrame.first
            }

            if (currentPlayer.frames.size >= 2) {
                val previousPreviousFrame = currentPlayer.frames[currentPlayer.frames.indexOf(previousFrame) - 1]
                if (previousPreviousFrame.isStrike()) {
                    if (previousFrame.isStrike()) {
                        previousPreviousFrame.bonus = previousFrame.first add currentFrame.first
                    } else {
                        previousPreviousFrame.bonus = previousFrame.first add previousFrame.second
                    }
                }
            }
        }

        currentPlayer.frames.add(currentFrame)
        currentBall = First
        currentFrame = Frame()
        currentPlayer = when (players.size) {
            players.indexOf(currentPlayer) + 1 -> players.first()
            else -> players[players.indexOf(currentPlayer) + 1]
        }
    }

    private infix fun Int.add(points: Int): Int {
        return this + points
    }

    private infix fun Int.addleTen(points: Int): Int {
        require(points <= 10)
        return this + points
    }

    private infix fun Int.addleTenTotal(points: Int): Int {
        require(points <= 10 - currentFrame.first)
        return this + points
    }

    private infix fun Int.addleTenTotalThird(points: Int): Int {
        require(points <= 10 - currentFrame.second)
        return this + points
    }
}
