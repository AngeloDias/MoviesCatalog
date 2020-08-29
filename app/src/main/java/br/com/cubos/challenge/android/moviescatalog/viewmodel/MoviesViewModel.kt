package br.com.cubos.challenge.android.moviescatalog.viewmodel

import androidx.lifecycle.ViewModel
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ApiMovieDataMapper
import br.com.cubos.challenge.android.moviescatalog.data.mapper.ListMapperImpl
import br.com.cubos.challenge.android.moviescatalog.data.repository.ApiTmdbRepositoryImpl

class MoviesViewModel: ViewModel() {
    private val listMapperImpl = ListMapperImpl(ApiMovieDataMapper())
    private lateinit var repositoryImpl: ApiTmdbRepositoryImpl

}