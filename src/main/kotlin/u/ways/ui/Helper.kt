package u.ways.ui

import u.ways.domain.Ball
import u.ways.domain.Ball.Second
import u.ways.domain.Frame
import u.ways.domain.Player

object Helper {
    fun parseInput(input: String, frame: Frame): Int {
        return when (input) {
            "X", "x" -> 10
            "/"      -> 10 - frame.first
            "-"      -> 0
            else     -> input.toInt()
        }
    }

    fun validate(pinsDropped: Int, frame: Frame, ball: Ball, player: Player): Boolean {
        if (pinsDropped < 0 || pinsDropped > 10) {
            println("\nerror: invalid input of pins dropped. (pins dropped should be within the range of 0 to 10,\n" +
                    "       - can be used when no points scored, and x or / can be used when a strike or spare occurred.)")
            return false
        }
        if (ball == Second &&  (pinsDropped > 10 - frame.first) && (player.frames.size + 1 != 10)) {
            println("\nerror: invalid input of pins dropped on second ball. (total frame pins dropped should be equal or less than 10)\n")
            return false
        }
        return true
    }
}