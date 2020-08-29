package br.com.cubos.challenge.android.moviescatalog.data.api

import com.google.gson.annotations.SerializedName

data class ApiMovie(
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("genres")
    val genres: List<ApiGenre>
)

data class ApiGenre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
