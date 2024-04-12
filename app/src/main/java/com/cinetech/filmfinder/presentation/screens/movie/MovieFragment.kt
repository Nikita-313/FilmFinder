package com.cinetech.filmfinder.presentation.screens.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import com.cinetech.domain.models.CommentsResponse
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.app.appComponent
import com.cinetech.filmfinder.databinding.FragmentMovieBinding
import com.cinetech.filmfinder.presentation.helpers.LoadableData
import com.cinetech.filmfinder.presentation.screens.movie.model.MovieScreenState
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory.Factory

    private var movieName: String = ""

    private lateinit var vm: MovieViewModel

    private var prevState: MovieScreenState? = null
    private var lastCommentsResponse: CommentsResponse? = null

    private var posterAdapter: PosterAdapter? = null
    private var posterLayoutManager: LinearLayoutManager? = null

    private var commentsAdapter: CommentsAdapter? = null
    private var commentsLayoutManager: LinearLayoutManager? = null

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(inflater)
        movieName = arguments?.getString(MOVIE_NAME_KEY) ?: ""
        val movieId: Int = arguments?.getInt(MOVIE_ID_KEY) ?: 0
        vm = ViewModelProvider(this,movieViewModelFactory.create(movieId))[MovieViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigationBackButton()
        initPostersRecycler()
        initCommentsRecycler()
        initReloadData()
        listenData()
    }

    private fun initReloadData(){
        binding.errorMassageInclude.retryButton.setOnClickListener {
            vm.loadMovieData()
        }
        binding.errorMassageComments.retryButton.setOnClickListener {
            vm.reloadCommentsData()
        }
    }

    private fun initNavigationBackButton() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.topAppBar2.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.topAppBar.title = movieName
        binding.topAppBar2.title = movieName
    }

    private fun listenData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.movieStateFlow().collect {
                    if (it.movie != prevState?.movie) renderMovie(it)
                    if (it.comments != prevState?.comments) renderComments(it)
                }
            }
        }
    }

    private fun initPostersRecycler() {
        posterAdapter = PosterAdapter()
        posterLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        posterLayoutManager?.scrollToPosition(Int.MAX_VALUE / 2)
        binding.recyclerPosters.apply {
            adapter = posterAdapter
            layoutManager = posterLayoutManager
        }
    }

    private fun initCommentsRecycler() {
        commentsAdapter = CommentsAdapter()
        commentsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerComments.apply {
            adapter = commentsAdapter
            layoutManager = commentsLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == (adapter!!.itemCount) - 1) {
                        vm.nextCommentPage(lastCommentsResponse)
                    }
                }
            })
        }
    }

    private fun renderMovie(state: MovieScreenState) = when (state.movie) {
        is LoadableData.Error -> {
            binding.content.visibility = View.GONE
            binding.errorMassageInclude.errorMassage.visibility = View.VISIBLE
            binding.contentLoadingIndicator.visibility = View.GONE

        }

        is LoadableData.Loading -> {
            binding.content.visibility = View.GONE
            binding.errorMassageInclude.errorMassage.visibility = View.GONE
            binding.contentLoadingIndicator.visibility = View.VISIBLE

        }

        is LoadableData.Success -> {
            val movie = state.movie.value

            if (movie.shortDescription != null) {
                binding.shortDescription.text = movie.shortDescription
            } else {
                binding.shortDescription.visibility = View.GONE
            }

            if (movie.description != null) {
                binding.description.text = movie.description
            } else {
                binding.description.visibility = View.GONE
            }

            if (movie.kpRating != null) {
                binding.kpRating.text = String.format(Locale.US, "%.1f", movie.kpRating)
                movie.kpVotesNumber?.let {
                    val dec = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.ENGLISH))
                    binding.votesNumber.text = dec.format(it.dec()).replace(",", " ") + " " + resources.getString(R.string.fragment_movie_votes)
                }
            } else {
                binding.ratingLayout.visibility = View.GONE
            }

            if (!movie.similarMovies.isNullOrEmpty()) {
                posterAdapter?.items = movie.similarMovies ?: emptyList()
            } else {
                binding.postersLayout.visibility = View.GONE
            }

            movie.posterUrl?.let {
                val imageLoader = ImageLoader.Builder(requireContext())
                    .crossfade(true)
                    .build()

                val request = ImageRequest.Builder(requireContext())
                    .data(it)
                    .target(
                        onStart = { _ -> },
                        onSuccess = { result ->
                            binding.topAppBar.visibility = View.GONE
                            binding.collapsingToolbar.visibility = View.VISIBLE
                            binding.moviePoster.setImageDrawable(result)

                            binding.content.visibility = View.VISIBLE
                            binding.errorMassageInclude.errorMassage.visibility = View.GONE
                            binding.contentLoadingIndicator.visibility = View.GONE
                        },
                        onError = { _ ->
                            binding.content.visibility = View.VISIBLE
                            binding.errorMassageInclude.errorMassage.visibility = View.GONE
                            binding.contentLoadingIndicator.visibility = View.GONE
                        }
                    )
                    .build()
                imageLoader.enqueue(request)
            }
            Unit
        }
    }

    private fun renderComments(state: MovieScreenState) = when (state.comments) {
        is LoadableData.Error -> {
            binding.errorMassageComments.errorMassage.visibility = View.VISIBLE
            binding.recyclerComments.visibility = View.GONE
            commentsAdapter?.loadingIndicator = false
        }

        is LoadableData.Loading -> {
            binding.errorMassageComments.errorMassage.visibility = View.GONE
            binding.recyclerComments.visibility = View.VISIBLE
            commentsAdapter?.loadingIndicator = true
        }

        is LoadableData.Success -> {
            val commentsResponse = state.comments.value
            lastCommentsResponse = commentsResponse
            binding.errorMassageComments.errorMassage.visibility = View.GONE
            binding.recyclerComments.visibility = View.VISIBLE
            commentsAdapter?.loadingIndicator = false
            commentsAdapter?.items = state.comments.value.docs
            if (commentsResponse.page == commentsResponse.pages) {
                commentsAdapter?.loadingIndicatorCount = 0
            } else {
            }
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "MOVIE_ID_KEY"
        const val MOVIE_NAME_KEY = "MOVIE_NAME_KEY"
    }

}