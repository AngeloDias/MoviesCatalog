package br.com.cubos.challenge.android.moviescatalog.data.repository

import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import io.reactivex.Observable

interface ApiTmdbRepository {

    fun getPopularMovies(): Observable<List<Movie>>

}