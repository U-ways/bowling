package u.ways.ui

import u.ways.domain.Ball
import u.ways.domain.Ball.First
import u.ways.domain.Ball.Second
import u.ways.domain.Frame
import u.ways.domain.Player
import u.ways.ui.scorebored.Factory

object Component {
    internal val banner: String = """
        888888b.                          888 d8b                        .d8888b.  8888888b.  
        888  "88b                         888 Y8P                       d88P  Y88b 888  "Y88b 
        888  .88P                         888                                  888 888    888 
        8888888K.   .d88b.  888  888  888 888 888 88888b.   .d88b.           .d88P 888    888 
        888  "Y88b d88""88b 888  888  888 888 888 888 "88b d88P"88b      .od888P"  888    888 
        888    888 888  888 888  888  888 888 888 888  888 888  888     d88P"      888    888 
        888   d88P Y88..88P Y88b 888 d88P 888 888 888  888 Y88b 888     888"       888  .d88P 
        8888888P"   "Y88P"   "Y8888888P"  888 888 888  888  "Y88888     888888888  8888888P"  
                                                                888                           
                                                           Y8b d88P                           
                                                            "Y88P"
        """.trimIndent()

    internal val help: String = """
        Options:
            help          - display the list of available commands
            start         - starts a game of bowling with the current list of registered players
            add    [name] - registers a player with the given name
            remove [name] - removes a player with the given name
            players       - display current registered players
            u.ways.exit          - exits the game
        """.trimIndent()

    internal val inGameHelp: String = """
        Options:
            help  - display the list of available commands
            rules - display the rules of bowling
            end   - ends the game
        """.trimIndent()

    internal val rules: String = """
        A game of bowling consists of ten frames. In each frame, the bowler will have two chances to 
        knock down as many pins as possible with his bowling ball. 
    
        In games with more than one bowler, every bowler will take his frame in a predetermined order before 
        the next frame begins. In general, one point is scored for each pin that is knocked over. So if a player
        bowls over three pins with the first shot, then six with the second, the player would receive a total of 
        nine points for that frame. If a player knocks down 9 pins with the first shot, but misses with the second, 
        the player would also score nine. 
    
        In the event that all ten pins are knocked over by a player in a single frame, bonuses are awarded: 
          - If a bowler is able to knock down all ten pins with the first ball, it is known  as a strike. 
          - If the bowler is able to knock down all 10 pins with the two balls of a frame, it is known as a spare. 
        
        Bonus points are awarded for both of these, depending on what is scored in the next 2 balls (for a strike) 
        or 1 ball (for a spare). If the bowler knocks down all 10 pins in the tenth frame, the  bowler is allowed to 
        throw 3 balls for that frame. This allows for a potential of 12 strikes in a single game, and a maximum 
        score of 300 points, a perfect game.
        """.trimIndent()

    internal fun winner(players: List<Player>): String {
        val highestScore = players.filter { player ->
            player.calculateTotalScore() == (players.maxBy { it.calculateTotalScore() }!!.calculateTotalScore())
        }

        val outcome = when {
            highestScore.size > 1 -> {
                val draw = highestScore.joinToString(" and ") { it.name }
                "It is a draw between: $draw"
            }
            else -> {
                val winner = highestScore.first().name
                "$winner has won the game!"
            }
        }

        val border = "##########" + "#".repeat(outcome.length)
        return "$border\n#    $outcome    #\n$border"
    }

    internal fun gameSummary(players: List<Player>): String {
        val scoreboards = players.joinToString("\n") {
            val scoreboard = Factory.create(it.frames)
            "${it.name} final scoreboard:\n$scoreboard"
        }
        val summary = players.joinToString { "[Name: ${it.name}, Score: ${it.calculateTotalScore()}]" }
        return "$scoreboards\n\nGame Summary: $summary \n"
    }

    internal fun players(players: MutableSet<String>): String = when {
        BowlingUi.registeredPlayers.isEmpty() ->
            "No players registered yet. (Tip: to register a player type: add [name])"
        else ->
            "Current Registered player:\n${players.joinToString(prefix = "[", postfix = "]")}"
    }

    internal fun currentGameStatus(player: Player, ball: Ball): String =
        "> ${player.name}@F${player.frames.size + 1}:B${ball.ordinal + 1}"

    internal fun inGameIntroduction(players: MutableSet<String>): String =
        "You've started a bowling session.\n" +
        "Each player should input the pins they dropped in each ball as instructed.\n\n" +
        "${inGameHelp}\n" +
        "\nNumber of players: ${players.size} (${players.first()} goes first)\n" +
        "\n========================================================================\n"

    internal fun ballOutcome(pinsDropped: Int, frame: Frame, ball: Ball, player: Player): String = when {
        ball == First && pinsDropped == 10 ->
            "\n--- Strike! Awarded for ${player.name}! ---\n"
        ball == Second && (frame.first + pinsDropped == 10) && frame.first != 10 ->
            "\n--- Spare! Awarded for ${player.name}! ---\n"
        else -> ""
    }
}