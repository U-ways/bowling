package u.ways.domain

class Player(
    val name: String,
    var frames: MutableList<Frame> = mutableListOf()
) {
    internal fun calculateTotalScore(): Int = frames.sumBy { it.calculateScore() }
}