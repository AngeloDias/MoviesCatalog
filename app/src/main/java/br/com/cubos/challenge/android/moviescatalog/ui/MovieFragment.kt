package br.com.cubos.challenge.android.moviescatalog.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.utils.CheckNetwork
import br.com.cubos.challenge.android.moviescatalog.ui.adapter.MoviesGridViewAdapter
import br.com.cubos.challenge.android.moviescatalog.utils.Status
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

        setupViewModel(view.context)
        setupObserver()

        moviesGridView.emptyView = view.findViewById(R.id.emptyGridTextView)
        moviesGridView.adapter = gridViewAdapter
    }

    private fun setupViewModel(viewContext: Context){
        moviesByGenreViewModel = ViewModelProvider(
            this,
            ViewModelFactory(movieGenre, "", viewContext)
        ).get(MoviesByGenreViewModel::class.java)
    }

    private fun setupObserver(){
        moviesByGenreViewModel.moviesByGenreLiveData.observe(this, {
            when(it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    gridViewAdapter.refreshData(it.data!!)
                }
                Status.ERROR -> {
                    if(it.message.equals(CheckNetwork.ERROR_INTERNET_NOT_AVAILABLE)) {
                        // TODO() start listening a callback to network connection or using another approach, acquire
                    }
                }
            }
        })
    }

}
