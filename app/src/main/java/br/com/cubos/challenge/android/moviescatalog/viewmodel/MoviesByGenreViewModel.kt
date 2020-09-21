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
import br.com.cubos.challenge.android.moviescatalog.ui.MovieGenreTypes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesByGenreViewModel(private val enumGenre: MovieGenreTypes): ViewModel() {
    //region Declaring properties

    private val listMapperImpl: ListMapper<ApiMovie, Movie>
    private val repositoryImpl: ApiTmdbRepositoryImpl

    private val _moviesByGenreMutableLiveData = MutableLiveData<ArrayList<Movie>>()
    val moviesByGenreLiveData: LiveData<ArrayList<Movie>> = _moviesByGenreMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    //endregion

    init {
        listMapperImpl = ListMapperImpl(ApiMovieDataMapper())
        repositoryImpl = ApiTmdbRepositoryImpl(listMapperImpl)

        synchronized(this) {
            fetchMoviesByGenres(enumGenre)
        }
    }

    private fun fetchMoviesByGenres(enumGenre: MovieGenreTypes) {
        val genre = ArrayList<String>()

        genre.add(enumGenre.genreId)

        val disposableMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _moviesByGenreMutableLiveData.postValue(it as ArrayList<Movie>?)
                },
                {
                    Log.e("ApiError", it.message!!)
                }
            )

        compositeDisposable.add(disposableMovies)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
