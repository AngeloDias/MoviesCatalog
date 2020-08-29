package br.com.cubos.challenge.android.moviescatalog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(context) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
