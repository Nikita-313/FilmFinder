package com.cinetech.filmfinder.presentation.screens.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.cinetech.filmfinder.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }
}