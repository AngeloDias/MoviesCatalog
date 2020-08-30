package br.com.cubos.challenge.android.moviescatalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cubos.challenge.android.moviescatalog.data.api.ApiMovie
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ApiMovieDataMapper
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ListMapper
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ListMapperImpl
import br.com.cubos.challenge.android.moviescatalog.data.repository.ApiTmdbRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesViewModel: ViewModel() {
    //region Declaring properties

    private val listMapperImpl: ListMapper<ApiMovie, Movie>
    private val repositoryImpl: ApiTmdbRepositoryImpl

    private val _actionMovies = MutableLiveData<List<Movie>>()
    val actionMovies = _actionMovies

    private val _fantasyMovies = MutableLiveData<List<Movie>>()
    val fantasyMovies = _fantasyMovies

    private val _fictionMovies = MutableLiveData<List<Movie>>()
    val fictionMovies = _fictionMovies

    private val _dramaMovies = MutableLiveData<List<Movie>>()
    val dramaMovies: LiveData<List<Movie>> = _dramaMovies

    private val compositeDisposable = CompositeDisposable()

    //endregion

    init {
        listMapperImpl = ListMapperImpl(ApiMovieDataMapper())
        repositoryImpl = ApiTmdbRepositoryImpl(listMapperImpl)

        synchronized(this) {
            fetchMoviesByGenres()
        }
    }

    private fun fetchMoviesByGenres() {
        val genre = ArrayList<String>()
        genre.add("18")

        val disposableDramaMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _dramaMovies.postValue(it)
                    it.forEach {m ->
                        Log.d("ApiSuccess", "Drama Original title: ${m.originalTitle}")
                    }
                },
                {
                    Log.e("ApiError", it.message!!)
                }
            )

        genre.clear()
        genre.add("28")

        val disposableActionMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _actionMovies.postValue(it)
                },
                {
                    Log.e("ApiError", it.message!!)
                }
            )

        genre.clear()
        genre.add("878")

        val disposableFictionMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _fictionMovies.postValue(it)
                },
                {
                    Log.e("ApiError", it.message!!)
                }
            )

        genre.clear()
        genre.add("14")

        val disposableFantasyMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _fantasyMovies.postValue(it)
                },
                {
                    Log.e("ApiError", it.message!!)
                }
            )

        compositeDisposable.addAll(
            disposableDramaMovies,
            disposableActionMovies,
            disposableFantasyMovies,
            disposableFictionMovies
        )

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
