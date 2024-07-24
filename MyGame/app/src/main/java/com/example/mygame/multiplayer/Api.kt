package com.example.mygame.multiplayer

import com.example.mygame.domain.leaderboard.Leaderboard
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Repository {

    private val gson: Gson = GsonBuilder()
        .setStrictness(Strictness.LENIENT)
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.10.29.46:8000/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val api = retrofit.create(Api::class.java)

    suspend fun getLeaderboard(): Leaderboard? =
        withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                api.getScoreboard().enqueue(object : Callback<LeaderboardJson> {
                    override fun onResponse(
                        call: Call<LeaderboardJson>,
                        response: Response<LeaderboardJson>
                    ) {
                        when (val scoreboard = response.body()) {
                            null -> continuation.resume(null)
                            else -> continuation.resume(scoreboard.asModel())
                        }

                    }

                    override fun onFailure(call: Call<LeaderboardJson>, t: Throwable) {
                        continuation.resume(null)
                    }
                })
            }
        }

}

data class LeaderboardJson(
    @SerializedName("leaders")
    val leaders: List<LeaderJson>
) {

    fun asModel() = Leaderboard(
        leaders = leaders.map {
            Leaderboard.Leader(it.name, it.score)
        }
    )

}

data class LeaderJson(
    @SerializedName("name")
    val name: String,
    @SerializedName("score")
    val score: Int
)

interface Api {
    @GET("score/show")
    fun getScoreboard(): Call<LeaderboardJson>
}
