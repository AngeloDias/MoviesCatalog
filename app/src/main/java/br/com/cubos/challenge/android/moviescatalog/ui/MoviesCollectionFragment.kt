package br.com.cubos.challenge.android.moviescatalog.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.cubos.challenge.android.moviescatalog.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoviesCollectionFragment: Fragment() {
    private lateinit var moviesFragmentCollectionAdapter: MoviesFragmentCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_collection, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesFragmentCollectionAdapter = MoviesFragmentCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = moviesFragmentCollectionAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(resources.getString(R.color.yellow)))
        tabLayout.setTabTextColors(
            Color.parseColor(resources.getString(R.color.white)),
            Color.parseColor(resources.getString(R.color.white))
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Ação"
                }
                1 -> {
                    tab.text = "Drama"
                }
                2 -> {
                    tab.text = "Fantasia"
                }
                else -> {
                    tab.text = "Ficção"
                }
            }

        }.attach()

    }

}

class MoviesFragmentCollectionAdapter(
    fragmentRelatedToAdapter: Fragment): FragmentStateAdapter(fragmentRelatedToAdapter) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                MovieFragment(MovieGenreTypes.ACTION)
            }
            1 -> {
                MovieFragment(MovieGenreTypes.DRAMA)
            }
            2 -> {
                MovieFragment(MovieGenreTypes.FANTASY)
            }
            else -> {
                MovieFragment(MovieGenreTypes.FICTION)
            }
        }
    }

}
