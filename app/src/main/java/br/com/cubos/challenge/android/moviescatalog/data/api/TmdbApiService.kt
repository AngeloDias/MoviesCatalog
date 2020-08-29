package br.com.cubos.challenge.android.moviescatalog.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Observable<MoviesReturned>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("api_key")
        apiKey: String,

        @Query("with_genres")
        genresId: List<String>): Observable<MoviesReturned>

}
