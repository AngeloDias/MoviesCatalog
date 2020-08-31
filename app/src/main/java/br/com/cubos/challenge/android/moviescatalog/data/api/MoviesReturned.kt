package br.com.cubos.challenge.android.moviescatalog.data.api

import com.google.gson.annotations.SerializedName

data class MoviesReturned (
    @SerializedName("total_pages")
    val countPages: Int,
    @SerializedName("results")
    val movies: ArrayList<ApiMovie>
)
