package br.com.cubos.challenge.android.moviescatalog.data.api

import com.google.gson.annotations.SerializedName

data class ApiMovie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("genre_ids")
    val genres: List<Int>
)
