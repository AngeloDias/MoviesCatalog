package br.com.cubos.challenge.android.moviescatalog.data.mapper

import br.com.cubos.challenge.android.moviescatalog.data.api.ApiMovie
import br.com.cubos.challenge.android.moviescatalog.data.domain.Genre
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie

class ApiMovieDataMapper: Mapper<ApiMovie, Movie> {

    override fun map(input: ApiMovie): Movie {
        return Movie(
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview,
            posterPath = input.posterPath,
            genres = input.genres/*.map {
                Genre(it.id, it.name)
            }*/
        )
    }

}
