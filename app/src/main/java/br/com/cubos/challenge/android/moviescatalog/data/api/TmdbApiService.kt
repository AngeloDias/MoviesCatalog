package br.com.cubos.challenge.android.moviescatalog.data.api

import io.reactivex.Observable
import retrofit2.http.GET

interface TmdbApiService {

    @GET("movie/popular")
    fun getPopularMovies(): Observable<List<ApiMovie>>

}
