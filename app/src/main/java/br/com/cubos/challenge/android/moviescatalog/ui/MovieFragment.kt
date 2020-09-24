package br.com.cubos.challenge.android.moviescatalog.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.ui.adapter.MoviesGridViewAdapter
import br.com.cubos.challenge.android.moviescatalog.viewmodel.MoviesByGenreViewModel
import br.com.cubos.challenge.android.moviescatalog.viewmodel.ViewModelFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


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

        hasInternetAccess(view.context)
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

    private fun hasInternetAccess(context: Context): Boolean {
        if (isNetworkAvailable(context)) {
            try {
                val urlc: HttpURLConnection = URL("http://clients3.google.com/generate_204")
                    .openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Android")
                urlc.setRequestProperty("Connection", "close")

                urlc.connectTimeout = 1500

                urlc.connect()

                return urlc.responseCode == 204 && urlc.contentLength == 0
            } catch (e: IOException) {
                TODO()
            }
        } else {
            TODO()
        }

        return false
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo

        return activeNetworkInfo != null
    }

}
