package br.com.cubos.challenge.android.moviescatalog.viewmodel

import android.content.Context
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
import br.com.cubos.challenge.android.moviescatalog.utils.CheckNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesByTitleViewModel(
    private val title: String,
    private val viewContext: Context): ViewModel() {

    //region Declaring properties

    private val listMapperImpl: ListMapper<ApiMovie, Movie>
    private val repositoryImpl: ApiTmdbRepositoryImpl

    private val _moviesByTitleMutableLiveData = MutableLiveData<ArrayList<Movie>>()
    val moviesByTitleLiveData: LiveData<ArrayList<Movie>> = _moviesByTitleMutableLiveData

    private val compositeDisposable = CompositeDisposable()

    //endregion

    init {
        listMapperImpl = ListMapperImpl(ApiMovieDataMapper())
        repositoryImpl = ApiTmdbRepositoryImpl(listMapperImpl)

        synchronized(this) {
            fetchMoviesByTitle(title)
        }
    }

    private fun fetchMoviesByTitle(title: String) {
        val disposableMovies = repositoryImpl
            .getMoviesByTitle(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _moviesByTitleMutableLiveData.postValue(it as ArrayList<Movie>?)
                },
                {
                    Log.e("ApiError", it.message!!)
                    CheckNetwork().checkIfDeviceIsReadyToConnectInternet(viewContext)
                }
            )

        compositeDisposable.add(disposableMovies)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
