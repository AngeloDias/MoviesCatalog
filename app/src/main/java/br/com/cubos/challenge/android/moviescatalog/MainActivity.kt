package br.com.cubos.challenge.android.moviescatalog

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.cubos.challenge.android.moviescatalog.ui.MovieGenreTypes
import br.com.cubos.challenge.android.moviescatalog.ui.MoviesCollectionFragment
import br.com.cubos.challenge.android.moviescatalog.viewmodel.MoviesByTitleViewModel
import br.com.cubos.challenge.android.moviescatalog.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(findViewById<FrameLayout>(R.id.fragments_placeholder) != null) {
            if (savedInstanceState != null) {
                return
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.fragments_placeholder, MoviesCollectionFragment())
                .commit()
        }

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                val moviesByTitleViewModel = ViewModelProvider(
                    this,
                    ViewModelFactory(MovieGenreTypes.DEFAULT, query, applicationContext)
                ).get(MoviesByTitleViewModel::class.java)

                moviesByTitleViewModel.moviesByTitleLiveData.observe(this, {})

                Toast.makeText(applicationContext, "Pegou pesquisa", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search_item).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = true
        }

        return true
    }

}
