package br.com.cubos.challenge.android.moviescatalog.data.repository

import br.com.cubos.challenge.android.moviescatalog.BuildConfig
import br.com.cubos.challenge.android.moviescatalog.data.api.ApiMovie
import br.com.cubos.challenge.android.moviescatalog.data.api.ApiRetrofitService
import br.com.cubos.challenge.android.moviescatalog.data.api.TmdbApiService
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ListMapper
import io.reactivex.Observable

class ApiTmdbRepositoryImpl(private val listMapper: ListMapper<ApiMovie, Movie>): ApiTmdbRepository {

    override fun getPopularMovies(): Observable<List<Movie>> {
        val popularApiMovies = getRetrofitService()
            .getPopularMovies("98fd2f0ffa80a2790cc4a4e5d611e1a3")

        return popularApiMovies.map {
            listMapper.map(it.movies)
        }
    }

    override fun getMoviesByGenres(genres: List<String>): Observable<List<Movie>> {
        val apiMoviesByGenre = getRetrofitService()
            .getMoviesByGenre(BuildConfig.TMDB_API_KEY, genres)

        return apiMoviesByGenre.map {
            listMapper.map(it.movies)
        }
    }

    override fun getMoviesByTitle(title: String): Observable<List<Movie>> {
        val apiMoviesByTitle = getRetrofitService().getMoviesByTitle("98fd2f0ffa80a2790cc4a4e5d611e1a3", title)

        return apiMoviesByTitle.map {
            listMapper.map(it.movies)
        }
    }

    private fun getRetrofitService(): TmdbApiService {
        return ApiRetrofitService.getInstance().create(TmdbApiService::class.java)
    }

}
