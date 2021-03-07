package u.ways.ui.scorebored

class Scoreboard {
    private val display = arrayOf<MutableList<String>>(
        mutableListOf(), // ┌────┬────┐ ┌────┬────┬────┐
        mutableListOf(), // │ $f │ $s │ │ $f │ $s │ $t │
        mutableListOf(), // │    └────┤ │    └────┴────┤
        mutableListOf(), // │ $ttl    │ │ $ttl         │
        mutableListOf()  // └─────────┘ └──────────────┘
    )

    fun add(total: String, first: String, second: String, third: String = "unused") {
        val f = first.padEnd(2)
        val s = second.padEnd(2)
        val t = third.padEnd(2)
        val ttl = total.padEnd(4)

        if (third == "unused") {
            display[0].add("┌────┬────┐")
            display[1].add("│ $f │ $s │")
            display[2].add("│    └────┤")
            display[3].add("│ $ttl    │")
            display[4].add("└─────────┘")
        } else {
            display[0].add("┌────┬────┬────┐")
            display[1].add("│ $f │ $s │ $t │")
            display[2].add("│    └────┴────┤")
            display[3].add("│ $ttl         │")
            display[4].add("└──────────────┘")
        }
    }

    override fun toString(): String {
        return when {
            display[0].isNotEmpty() ->
                display.joinToString("\n") { line -> line.joinToString("") }
            else -> ""
        }
    }
}