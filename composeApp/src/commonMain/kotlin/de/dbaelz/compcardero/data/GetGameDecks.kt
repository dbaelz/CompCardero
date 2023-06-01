package de.dbaelz.compcardero.data

import de.dbaelz.compcardero.data.game.GameDeck

sealed interface GetGameDecks {
    operator fun invoke(): List<GameDeck>
}

class GetGameDecksImpl(private val gameDecks: List<GameDeck>) : GetGameDecks {
    override fun invoke(): List<GameDeck> {
        return gameDecks
    }
}
