package br.com.cubos.challenge.android.moviescatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.ui.adapter.MoviesGridViewAdapter
import br.com.cubos.challenge.android.moviescatalog.viewmodel.MoviesByGenreViewModel
import br.com.cubos.challenge.android.moviescatalog.viewmodel.ViewModelFactory

class MovieFragment(private val movieGenre: MovieGenreTypes) : Fragment() {
    private lateinit var moviesByGenreViewModel: MoviesByGenreViewModel
    private lateinit var gridViewAdapter: MoviesGridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val moviesGridView = view.findViewById<GridView>(R.id.moviesGridView)
        val inflater = LayoutInflater.from(view.context)
        gridViewAdapter = MoviesGridViewAdapter(inflater, ArrayList())

        setupViewModel()
        setupObserver()

        moviesGridView.adapter = gridViewAdapter
    }

    private fun setupViewModel(){
        moviesByGenreViewModel = ViewModelProvider(
            this,
            ViewModelFactory(movieGenre, "")
        ).get(MoviesByGenreViewModel::class.java)
    }

    private fun setupObserver(){
        moviesByGenreViewModel.moviesByGenreLiveData.observe(this, {
            gridViewAdapter.refreshData(it)
        })
    }

}
