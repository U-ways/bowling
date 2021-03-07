package u.ways

import u.ways.ui.Component.banner
import u.ways.ui.Component.help
import kotlin.system.exitProcess
import u.ways.ui.BowlingUi as ui

var active = true

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("$banner\n$help")

            while (active) {
                print("> ")
                val input = readLine() ?: continue
                val command = input.split(" ")

                when (command[0]) {
                    "help" -> ui.displayHelp()
                    "start" -> ui.startGame()
                    "add" -> ui.addPlayer(command[1])
                    "remove" -> ui.removePlayer(command[1])
                    "players" -> ui.displayPlayers()
                    "rules" -> ui.displayRules()
                    "exit" -> exit()
                    else -> println("error: command not recognised. (type `help` for options)")
                }
            }
        }

        private fun exit() {
            active = false
            println("Goodbye.\n")
            exitProcess(0)
        }
    }
}