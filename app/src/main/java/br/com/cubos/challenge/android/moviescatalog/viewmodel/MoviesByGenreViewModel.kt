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
import br.com.cubos.challenge.android.moviescatalog.ui.MovieGenreTypes
import br.com.cubos.challenge.android.moviescatalog.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesByGenreViewModel(private val enumGenre: MovieGenreTypes,
                             private val viewContext: Context): ViewModel() {
    //region Declaring properties

    private val listMapperImpl: ListMapper<ApiMovie, Movie>
    private val repositoryImpl: ApiTmdbRepositoryImpl

    private val _moviesByGenreMutableLiveData = MutableLiveData<Resource<ArrayList<Movie>>>()
    val moviesByGenreLiveData: LiveData<Resource<ArrayList<Movie>>> = _moviesByGenreMutableLiveData

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
        _moviesByGenreMutableLiveData.postValue(Resource.loading(ArrayList()))

        val disposableMovies = repositoryImpl
            .getMoviesByGenres(genre)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _moviesByGenreMutableLiveData.postValue(Resource.success(it as ArrayList<Movie>))
                },
                {
                    CheckNetwork().checkIfDeviceIsReadyToConnectInternet(viewContext)

                    if (CheckNetwork.isNetworkConnected) {
                        _moviesByGenreMutableLiveData.postValue(Resource.error(it.message?: "", null))
                    } else {
                        _moviesByGenreMutableLiveData.postValue(
                            Resource.error(
                                CheckNetwork.ERROR_INTERNET_NOT_AVAILABLE,
                                null))
                    }

                    Log.e("ApiError", it.message!!)
                    it.printStackTrace()
                }
            )

        compositeDisposable.add(disposableMovies)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
