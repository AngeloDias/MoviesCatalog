package br.com.cubos.challenge.android.moviescatalog.data.repository

import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import io.reactivex.Observable

interface ApiTmdbRepository {

    fun getPopularMovies(): Observable<List<Movie>>

    fun getMoviesByGenres(genres: List<String>): Observable<List<Movie>>

    fun getMoviesByTitle(title: String): Observable<List<Movie>>

}