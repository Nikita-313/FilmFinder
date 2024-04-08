package com.cinetech.filmfinder.presentation.screens.movie_search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinetech.domain.models.MovieDto
import com.cinetech.domain.models.SearchMoviesParam
import com.cinetech.domain.usecase.SearchMoviesByNameUseCase
import com.cinetech.filmfinder.app.App

import com.cinetech.filmfinder.databinding.FragmentMovieSearchBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieSearchFragment : Fragment() {
    private lateinit var binding: FragmentMovieSearchBinding
    private var movieSearchRecyclerAdapter:MovieSearchRecyclerAdapter? = null
    @Inject
    lateinit var searchMoviesByNameUseCase:SearchMoviesByNameUseCase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    private fun initRecyclerView(){
        movieSearchRecyclerAdapter = MovieSearchRecyclerAdapter(requireContext());
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieSearchRecyclerAdapter
        }
//        movieSearchRecyclerAdapter?.data = Array(20){MovieDto(
//            id = it.toLong(),
//            name = "Wolf from woll dtrit",
//            year = 2020,
//            ageRating = 18,
//            country = "",
//            subNameEu = ""
//        )}.toList()
        lifecycleScope.launch {
            movieSearchRecyclerAdapter?.data = searchMoviesByNameUseCase.execute(SearchMoviesParam( "hello")).docs
        }
    }

}