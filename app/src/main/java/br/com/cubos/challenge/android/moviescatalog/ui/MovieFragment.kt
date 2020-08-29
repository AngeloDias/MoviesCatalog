package br.com.cubos.challenge.android.moviescatalog.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.viewmodel.MoviesViewModel
import br.com.cubos.challenge.android.moviescatalog.viewmodel.ViewModelFactory

/**
 * TODO
 *
 * Show the movies posters inside the fragment.
 * */
class MovieFragment : Fragment() {
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
//            val textView: TextView = view.findViewById(android.R.id.text1)
//            textView.text = getInt(ARG_OBJECT).toString()
//        }

        setupViewModel(view.context)
        setupObserver()
    }

    private fun setupObserver() {
        moviesViewModel.movies.observe(this, {movies ->
        })
    }

    private fun setupViewModel(context: Context) {
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(context)
        ).get(MoviesViewModel::class.java)
    }

}
