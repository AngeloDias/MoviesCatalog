package br.com.cubos.challenge.android.moviescatalog.data.repository

import br.com.cubos.challenge.android.moviescatalog.data.api.ApiMovie
import br.com.cubos.challenge.android.moviescatalog.data.api.ApiRetrofitFactory
import br.com.cubos.challenge.android.moviescatalog.data.api.TmdbApiService
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ListMapper
import io.reactivex.Observable

class ApiTmdbRepositoryImpl(private val listMapper: ListMapper<ApiMovie, Movie>): ApiTmdbRepository {

    override fun getPopularMovies(): Observable<List<Movie>> {
        val retrofit = ApiRetrofitFactory.getInstance()
        val service = retrofit.create(TmdbApiService::class.java)
        val observableList = service.getPopularMovies()

        return observableList.map {
            listMapper.map(it)
        }
    }

}
