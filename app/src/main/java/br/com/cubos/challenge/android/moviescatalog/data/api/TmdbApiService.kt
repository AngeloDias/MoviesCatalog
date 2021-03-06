package br.com.cubos.challenge.android.moviescatalog.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/popular")
    fun getPopularMovies(): Observable<MoviesReturned>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("with_genres")
        genresId: List<String>): Observable<MoviesReturned>

    @GET("")
    fun getMoviesByTitle(
        @Query("with_keywords")
        title: String): Observable<MoviesReturned>

}
