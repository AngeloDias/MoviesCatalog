package br.com.cubos.challenge.android.moviescatalog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.data.api.ApiRetrofitService
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie
import com.squareup.picasso.Picasso

class MovieDetailsFragment(private val movie: Movie) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val posterDetailImgView = view.findViewById<ImageView>(R.id.posterDetailMovieImageView)
        val overviewDetailTextView = view.findViewById<TextView>(R.id.movieOverviewTextView)

        Picasso
            .get()
            .load("${ApiRetrofitService.IMAGE_POSTER_PATH_URL}w500/${movie.posterPath}")
            .into(posterDetailImgView)

        overviewDetailTextView.text = movie.overview
    }

}