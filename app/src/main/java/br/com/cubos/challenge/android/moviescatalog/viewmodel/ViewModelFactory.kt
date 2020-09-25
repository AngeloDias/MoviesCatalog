package br.com.cubos.challenge.android.moviescatalog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.ui.MovieGenreTypes

class ViewModelFactory(
    private val enumGenre: MovieGenreTypes,
    private val title: String,
    private val viewContext: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesByGenreViewModel::class.java)) {
            return MoviesByGenreViewModel(enumGenre, viewContext) as T
        }

        if(modelClass.isAssignableFrom(MoviesByTitleViewModel::class.java)) {
            return MoviesByTitleViewModel(title, viewContext) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
