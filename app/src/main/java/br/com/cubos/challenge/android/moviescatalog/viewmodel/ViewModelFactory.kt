package br.com.cubos.challenge.android.moviescatalog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel() as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}
