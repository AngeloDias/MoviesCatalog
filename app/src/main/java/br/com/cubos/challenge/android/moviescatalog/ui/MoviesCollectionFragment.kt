package br.com.cubos.challenge.android.moviescatalog.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.cubos.challenge.android.moviescatalog.R
import br.com.cubos.challenge.android.moviescatalog.viewmodel.MoviesViewModel
import br.com.cubos.challenge.android.moviescatalog.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoviesCollectionFragment: Fragment() {
    private lateinit var moviesCollectionAdapter: MoviesCollectionAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var dataObserver: RecyclerView.AdapterDataObserver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesCollectionAdapter = MoviesCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)

        dataObserver = object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                moviesCollectionAdapter.notifyDataSetChanged()
            }
        }

        moviesCollectionAdapter.registerAdapterDataObserver(dataObserver)

        viewPager.adapter = moviesCollectionAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff00"))
        tabLayout.setTabTextColors(Color.parseColor("#ffffffff"), Color.parseColor("#ffffffff"))

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

        setupViewModel()
        setupObservers()

        dataObserver.onChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesCollectionAdapter.unregisterAdapterDataObserver(dataObserver)
    }

    private fun setupViewModel(){
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        ).get(MoviesViewModel::class.java)
    }

    private fun setupObservers(){
        moviesViewModel.dramaMovies.observe(this, {})
        moviesViewModel.actionMovies.observe(this, {})
        moviesViewModel.fictionMovies.observe(this, {})
        moviesViewModel.fantasyMovies.observe(this, {})
    }

}

class MoviesCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MovieFragment()

        return fragment
    }

}
