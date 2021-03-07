package u.ways.ui.scorebored

import u.ways.domain.Frame

object Factory {
    fun create(frames: MutableList<Frame>): String {
        val scoreboard = Scoreboard()

        frames.forEachIndexed { index, frame ->
            when {
                index < 9 -> {
                    val total = translateTotal(frames, frame)
                    val first = translateFirstBall(frame)
                    val second = translateSecondBall(frame)
                    scoreboard.add(total, first, second)
                }
                index == 9 -> {
                    val total = frames.sumBy { it.calculateScore() }.toString()
                    val first = translateFirstBallTenthFrame(frame)
                    val second = translateSecondBallTenthFrame(frame)
                    val third = translateThirdBall(frame)
                    scoreboard.add(total, first, second, third)
                }
                else -> error("error: total frames should be less than or equal to 10.")
            }
        }

        return "$scoreboard"
    }

    private fun translateTotal(frames: MutableList<Frame>, frame: Frame): String {
        val precedingFrames = frames.subList(0, frames.indexOf(frame))
        val proceedingFrames = frames.subList(frames.indexOf(frame) + 1, frames.size)

        val decision = when {
            precedingFrames.size == 8 -> when {
                frame.isStrike() || frame.isSpare() ->
                    if (proceedingFrames.isNotEmpty()) "calculate score"
                    else "incomplete: more preceding frames needed to calculate frame bonus"
                else -> "calculate score"
            }
            frame.isStrike() ->
                when (proceedingFrames.size) {
                    0 -> "incomplete: more preceding frames needed to calculate frame bonus"
                    1 ->
                        if (proceedingFrames.last().isStrike().not()) "calculate score"
                        else "incomplete: more preceding frames needed to calculate frame bonus"
                    else -> "calculate score"
                }
            frame.isSpare() ->
                if (proceedingFrames.isNotEmpty()) "calculate score"
                else "incomplete: more preceding frames needed to calculate frame bonus"
            else -> "calculate score"
        }

        return when {
            (decision == "calculate score") -> {
                val previousFramesTotal = precedingFrames.sumBy { it.calculateScore() }
                "${previousFramesTotal + frame.calculateScore()}"
            }
            else -> " "
        }
    }

    private fun translateFirstBall(frame: Frame): String = when {
        frame.isStrike() -> " "
        frame.first == 0 -> "-"
        else -> "${frame.first}"
    }

    private fun translateSecondBall(frame: Frame): String = when {
        frame.isStrike() -> "X"
        frame.isSpare() -> "/"
        frame.second == 0 -> "-"
        else -> "${frame.second}"
    }

    private fun translateThirdBall(frame: Frame): String = when {
        frame.third == 10 -> "X"
        (frame.second + frame.third) == 10 -> "/"
        frame.third == 0 -> "-"
        else -> "${frame.third}"
    }

    private fun translateFirstBallTenthFrame(frame: Frame): String = when {
        frame.first == 10 -> "X"
        frame.second == 0 -> "-"
        else -> "${frame.second}"
    }

    private fun translateSecondBallTenthFrame(frame: Frame): String = when {
        frame.second == 10 -> "X"
        (frame.first + frame.second) == 10 -> "/"
        frame.second == 0 -> "-"
        else -> "${frame.second}"
    }
}