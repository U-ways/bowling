package u.ways.domain

class Frame(
    var first: Int = 0,
    var second: Int = 0,
    var third: Int = 0,
    var bonus: Int = 0
) {
    internal fun isStrike(): Boolean = first == 10
    internal fun isSpare(): Boolean = !isStrike() && (first + second == 10)
    internal fun calculateScore(): Int = first + second + third + bonus
}