package br.com.cubos.challenge.android.moviescatalog.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
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

class MoviesViewModel(private val context: Context): ViewModel() {
    //region Declaring properties

    private val listMapperImpl: ListMapper<ApiMovie, Movie>
    private val repositoryImpl: ApiTmdbRepositoryImpl

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val compositeDisposable = CompositeDisposable()

    //endregion

    init {
        listMapperImpl = ListMapperImpl(ApiMovieDataMapper())
        repositoryImpl = ApiTmdbRepositoryImpl(listMapperImpl)

        fetchPopularMovies()
    }

    /**
     * TODO
     *
     * The movies are being posted on LiveData.
     * */
    private fun fetchPopularMovies() {

        compositeDisposable.add(
            repositoryImpl
                .getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _movies.postValue(it)
//                        it.forEach {movie ->
//                            Log.d("ApiDebug", "Movie original title: ${movie.originalTitle}")
//                        }
                    },
                    {
                        Toast.makeText(context, "Got an error", Toast.LENGTH_LONG).show()
                        Log.e("ApiError", it.message!!)
                    }
                )
        )

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}