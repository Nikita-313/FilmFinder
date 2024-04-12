package com.cinetech.filmfinder.presentation.screens.movie_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.app.appComponent
import com.cinetech.filmfinder.databinding.FragmentMovieSearchBinding
import com.cinetech.filmfinder.presentation.screens.movie.MovieFragment
import com.cinetech.filmfinder.presentation.screens.movie_search.model.MoviesUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.MovieSearchFilterFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieSearchFragment : Fragment() {
    private lateinit var binding: FragmentMovieSearchBinding

    private var movieRecyclerAdapter: MovieRecyclerAdapter? = null
    private var movieAdapterLayoutManager: LinearLayoutManager? = null

    private var searchRecyclerAdapter: SearchRecyclerAdapter? = null
    private var searchAdapterLayoutManager: LinearLayoutManager? = null

    @Inject
    lateinit var movieSearchViewModelFactory: MovieSearchViewModelFactory

    val vm: MovieSearchViewModel by viewModels {
        movieSearchViewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovieRecyclerView()
        initSearchRecyclerView()
        initSearchTextInput()
        initRetryButton()
        initFilterButton()
        listenSearchMovie()
        listenSearchLoadingIndicator()
        listenFilterProgressIndicator()
        listenErrors()
        listenMovie()
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    private fun initMovieRecyclerView() {
        movieRecyclerAdapter = MovieRecyclerAdapter{id,name->
            if(id == null) return@MovieRecyclerAdapter
            gotoMovieScreen(id,name)
        }
        movieAdapterLayoutManager = LinearLayoutManager(requireContext())
        binding.movieRecyclerView.apply {
            layoutManager = movieAdapterLayoutManager
            adapter = movieRecyclerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == (adapter!!.itemCount) - 1) {
                        vm.nextMoviesPage()
                    }
                }
            })
        }
    }

    private fun initSearchRecyclerView() {
        searchRecyclerAdapter = SearchRecyclerAdapter{id,name->
            if(id == null) return@SearchRecyclerAdapter
            gotoMovieScreen(id,name)
        }
        searchAdapterLayoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.apply {
            layoutManager = searchAdapterLayoutManager
            adapter = searchRecyclerAdapter
        }
    }

    private fun gotoMovieScreen(id:Int,name:String?){
        val bundle = Bundle()
        bundle.putInt(MovieFragment.MOVIE_ID_KEY,id)
        bundle.putString(MovieFragment.MOVIE_NAME_KEY,name)
        findNavController().navigate(R.id.movieFragment,bundle)
    }

    private fun initSearchTextInput() {
        binding.searchView.editText.doAfterTextChanged {
            searchAdapterLayoutManager?.scrollToPosition(0)
            vm.searchMovie(SearchMoviesParam(movieName = it.toString()))
        }
    }

    private fun initRetryButton() {
        binding.retryButton.setOnClickListener {
            vm.retryLoadMovies()
        }
    }

    private fun initFilterButton() {
        binding.searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_menu_filter -> showFilterDialog()
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun showFilterDialog() {
        val dialog = MovieSearchFilterFragment(vm.getLastLoadParam()) {
            vm.setNewLoadParam(it)
        }
        dialog.show(childFragmentManager, "Filter dialog")
    }

    private fun listenMovie() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.moviesSateFlow().collect {
                    when (it) {
                        is MoviesUiState.Success -> {
                            movieRecyclerAdapter?.loadingIndicator = false
                            binding.errorMassage.visibility = View.GONE
                            movieRecyclerAdapter?.data = it.movies
                            movieRecyclerAdapter?.lastPage = it.pages == it.pageNumber

                            if(it.pageNumber == 1) {
                                movieAdapterLayoutManager?.scrollToPosition(0)
                            }

                            if(it.movies.isEmpty()) {
                                binding.movieClearText.visibility = View.VISIBLE
                            } else {
                                binding.movieClearText.visibility = View.GONE
                            }
                        }

                        is MoviesUiState.Error -> {
                            movieRecyclerAdapter?.loadingIndicator = false
                            binding.errorMassage.visibility = View.VISIBLE
                            binding.movieClearText.visibility = View.GONE
                        }

                        is MoviesUiState.Loading -> {
                            movieRecyclerAdapter?.loadingIndicator = true
                            binding.errorMassage.visibility = View.GONE
                            binding.movieClearText.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun listenFilterProgressIndicator() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.filterLoadIndicatorSateFlow().collect {
                    if (it) binding.filterProgressIndicator.visibility = View.VISIBLE
                    else binding.filterProgressIndicator.visibility = View.GONE
                }
            }
        }
    }

    private fun listenSearchMovie() {
        vm.searchResultLive().observe(viewLifecycleOwner) {
            searchRecyclerAdapter?.items = it
            binding.apply {
                if (it.isEmpty() && searchView.editText.text.isEmpty()) {
                    noHistText.visibility = View.VISIBLE
                } else {
                    noHistText.visibility = View.GONE
                }
            }
        }
    }

    private fun listenSearchLoadingIndicator() {
        vm.searchLoadingIndicatorLive().observe(viewLifecycleOwner) {
            if (it) binding.searchProgressIndicator.visibility = View.VISIBLE
            else binding.searchProgressIndicator.visibility = View.INVISIBLE
        }
    }

    private fun listenErrors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.errorFlow().collect {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}