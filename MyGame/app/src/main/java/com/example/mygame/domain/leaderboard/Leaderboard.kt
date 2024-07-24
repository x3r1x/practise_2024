package com.example.mygame.domain.leaderboard

class Leaderboard(
    val leaders: List<Leader>
) {

    data class Leader(
        val name: String,
        val score: Int
    )

}
