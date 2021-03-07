package u.ways.ui

import u.ways.domain.Player
import u.ways.service.BowlingService
import u.ways.ui.Component.ballOutcome
import u.ways.ui.Component.currentGameStatus
import u.ways.ui.Component.gameSummary
import u.ways.ui.Component.help
import u.ways.ui.Component.inGameHelp
import u.ways.ui.Component.inGameIntroduction
import u.ways.ui.Component.players
import u.ways.ui.Component.rules
import u.ways.ui.Component.winner
import u.ways.ui.Helper.parseInput
import u.ways.ui.Helper.validate
import u.ways.ui.scorebored.Factory
import u.ways.ui.BowlingUi.bowlingService as bs

object BowlingUi {
    var gameRunning: Boolean = false
    var registeredPlayers: MutableSet<String> = mutableSetOf()
    private lateinit var bowlingService: BowlingService

    fun startGame() {
        if (registeredPlayers.isEmpty()) {
            println("error: At least one player should be registered before starting the game.\n")
            return endGame()
        }

        gameRunning = true
        bowlingService = BowlingService(registeredPlayers.map { Player(it) })

        println(inGameIntroduction(registeredPlayers))

        while (gameRunning) {
            println(Factory.create(bs.currentPlayer.frames))
            print("${currentGameStatus(bs.currentPlayer, bs.currentBall)} - pins dropped: ")
            val input = readLine() ?: continue

            try {
                val pinsDropped: Int = parseInput(input, bs.currentFrame)
                if (validate(pinsDropped, bs.currentFrame, bs.currentBall, bs.currentPlayer)) {
                    println(ballOutcome(pinsDropped, bs.currentFrame, bs.currentBall, bs.currentPlayer))
                    bs.addPoints(pinsDropped)
                } else continue
            } catch (invalid: Exception) {
                when (input) {
                    "help" -> println(inGameHelp)
                    "rules" -> println(rules)
                    "end" -> endGame()
                    else -> println("error: command not recognised. (type `help` for options)\n")
                }
            }

            gameRunning = gameRunning && !bs.gameOver()
        }

        println(gameSummary(bs.players))
        println(winner(bs.players))
    }

    fun endGame() {
        gameRunning = false
    }

    fun addPlayer(name: String?) = when {
        (name == null) ->
            println("error: please specify the name of the player to add. (e.g. `add Joe`)")
        (registeredPlayers.contains(name)) ->
            println("error: $name already registered as a player.")
        else -> {
            registeredPlayers.add(name)
            println("success: added a new player with name: $name")
        }
    }

    fun removePlayer(name: String?) = when {
        (name == null) ->
            println("error: please specify the name of the player to remove. (e.g. `remove Joe`)")
        (!registeredPlayers.contains(name)) ->
            println("error: player with the name: $name, does not exist.")
        else -> {
            registeredPlayers.remove(name)
            println("success: removed player with name: $name")
        }
    }

    fun displayHelp() = println(help)
    fun displayRules() = println(rules)
    fun displayPlayers() = println(players(registeredPlayers))
}
