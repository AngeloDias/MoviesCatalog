package br.com.cubos.challenge.android.moviescatalog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.data.domain.Movie

class MoviesGridViewAdapter(
    private val inflaterFromAppContext: LayoutInflater,
    private var movies: ArrayList<Movie>): BaseAdapter() {

    fun refreshData(movies: ArrayList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(p0: Int): Movie {
        return movies[p0]
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val viewHolderItem: MovieInGridViewHolderItem
        var view = p1

        if(p1 == null) {
            view = inflaterFromAppContext.inflate(R.layout.grid_item_movie, null)
            viewHolderItem = MovieInGridViewHolderItem(view)

        } else {
            viewHolderItem = p1.tag as MovieInGridViewHolderItem
        }

        val item = getItem(p0)

//        Picasso
//            .get()
//            .load("${ApiRetrofitFactory.MOVIE_API_BASE_URL}${item.posterPath}")
//            .into(viewHolderItem.posterMovieImageView)

//        viewHolderItem.posterMovieImageView.setImageURI(item.posterPath)
        viewHolderItem.titleMoviesTextView.text = item.originalTitle

        view!!.tag = viewHolderItem

        return view
    }

}

class MovieInGridViewHolderItem(view: View): RecyclerView.ViewHolder(view) {
    var posterMovieImageView: ImageView
    var titleMoviesTextView: TextView

    init {
        posterMovieImageView = view.findViewById(R.id.moviePosterImageView)
        titleMoviesTextView = view.findViewById(R.id.originalTitleTextView)
    }

}
