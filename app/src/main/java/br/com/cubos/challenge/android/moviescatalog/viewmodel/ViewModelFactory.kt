package br.com.cubos.challenge.android.moviescatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.ui.MovieGenreTypes

class ViewModelFactory(private val enumGenre: MovieGenreTypes): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(enumGenre) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
