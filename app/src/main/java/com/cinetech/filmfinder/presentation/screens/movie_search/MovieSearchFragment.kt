package com.cinetech.filmfinder.presentation.screens.movie_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.filmfinder.app.App

import com.cinetech.filmfinder.databinding.FragmentMovieSearchBinding
import javax.inject.Inject

class MovieSearchFragment : Fragment() {
    private lateinit var binding: FragmentMovieSearchBinding

    private var movieSearchRecyclerAdapter:MovieSearchRecyclerAdapter? = null
    private var adapterLayoutManager:LinearLayoutManager? = null

    @Inject
    lateinit var movieSearchFragmentFactory: MovieSearchFragmentFactory

    lateinit var vm: MovieSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieSearchBinding.inflate(inflater)
        vm = ViewModelProvider(this,movieSearchFragmentFactory)[MovieSearchViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchTextInput()
        listenMovie()
        listenLoadingIndicator()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    private fun initRecyclerView(){
        movieSearchRecyclerAdapter = MovieSearchRecyclerAdapter()
        adapterLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            layoutManager = adapterLayoutManager
            adapter = movieSearchRecyclerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == (adapter!!.itemCount) - 1) {
                        vm.nextPage()
                    }
                }
            })
        }
    }

    private fun initSearchTextInput(){
        binding.searchTextInput.doAfterTextChanged {
            adapterLayoutManager?.scrollToPosition(0)
            searchMovie(it.toString())
        }
    }

    private fun listenMovie(){
        vm.searchResultLive().observe(viewLifecycleOwner){
            movieSearchRecyclerAdapter?.data = it
        }
    }

    private fun listenLoadingIndicator(){
        vm.loadingDataIndicatorLive().observe(viewLifecycleOwner){
            movieSearchRecyclerAdapter?.loadingIndicator = it
        }
    }

    private fun searchMovie(name:String) {
        if(name.isEmpty()) return
        vm.searchMovie(SearchMoviesParam(movieName = name))
    }

}