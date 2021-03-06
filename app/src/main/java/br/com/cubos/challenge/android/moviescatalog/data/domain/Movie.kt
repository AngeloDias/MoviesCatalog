package br.com.cubos.challenge.android.moviescatalog.data.domain

data class Movie(
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val genres: List<Int>
)
