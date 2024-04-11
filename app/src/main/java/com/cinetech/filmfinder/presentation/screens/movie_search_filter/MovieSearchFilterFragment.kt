package com.cinetech.filmfinder.presentation.screens.movie_search_filter


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.app.appComponent
import com.cinetech.filmfinder.databinding.FragmentMovieSearchFilterBinding
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.LoadingCountryFilterUiState
import com.cinetech.filmfinder.presentation.screens.movie_search_filter.model.YearRangeFilterUiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject


class MovieSearchFilterFragment : BottomSheetDialogFragment() {

    private val vm: MovieSearchFilterViewModel by viewModels {
        movieSearchFilterViewModelFactory
    }

    private lateinit var binding: FragmentMovieSearchFilterBinding

    @Inject
    lateinit var movieSearchFilterViewModelFactory: MovieSearchFilterViewModelFactory

    private var countriesArray: Array<String> = emptyArray()
    private var checkedCountriesArray: BooleanArray = emptyArray<Boolean>().toBooleanArray()
    private var selectedYears: YearRangeFilterUiState? = null

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieSearchFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initYearDialogButton()
        initCountryDialogButton()
        initAgeRating()
        initRetryLoadDataButton()
        listenAgeRating()
        listenYear()
        listenLoadingCountry()
    }

    private fun initYearDialogButton() {
        binding.selectYearRangeButton.setOnClickListener {
            showYearRangeDialog()
        }
    }

    private fun initCountryDialogButton() {
        binding.selectCountyButton.setOnClickListener {
            showCountryDialog()
        }
    }

    private fun initAgeRating() {
        binding.ageRatingRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                vm.updateAgeRatingRange(slider.values[0].toInt(), slider.values[1].toInt())
            }

        })

    }

    private fun initRetryLoadDataButton() {
        binding.retryButton.setOnClickListener {
            vm.loadCountriesFromServer()
        }
    }

    private fun showYearRangeDialog() {
        val topYear = Calendar.getInstance().get(Calendar.YEAR)
        val bottomYear = 1887

        val d = Dialog(requireContext())

        d.setTitle("YearRangeDialog")
        d.setContentView(R.layout.dialog_number_range)
        val title: TextView = d.findViewById(R.id.title)
        val resetButton: Button = d.findViewById(R.id.resetButton)
        val doneButton: Button = d.findViewById(R.id.doneButton)
        val npFrom: NumberPicker = d.findViewById(R.id.numberPickerFrom)
        val npTo: NumberPicker = d.findViewById(R.id.numberPickerTo)

        title.text = resources.getString(R.string.fragment_movie_search_filter_year)

        npFrom.setOnValueChangedListener { _, _, newVal ->
            npTo.setMinValue(newVal)
            npTo.value = newVal
        }
        npFrom.setMaxValue(topYear)
        npFrom.setMinValue(bottomYear)
        npFrom.setFormatter(object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                if (value == npFrom.minValue) return resources.getString(R.string.fragment_movie_search_filter_from)
                return value.toString()
            }
        })

        npTo.setMaxValue(topYear)
        npTo.setMinValue(bottomYear)
        npTo.setFormatter(object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                if (value == npTo.minValue) return resources.getString(R.string.fragment_movie_search_filter_to)
                return value.toString()
            }
        })

        selectedYears?.let {
            npFrom.value = bottomYear
            npTo.value = bottomYear
            if (it.from != -1) {
                npFrom.value = it.from
                npTo.minValue = it.from
            }
            if (it.to != -1) npTo.value = it.to
        }

        resetButton.setOnClickListener {
            npFrom.minValue = bottomYear
            npTo.minValue = bottomYear
            npFrom.value = bottomYear
            npTo.value = bottomYear
        }

        doneButton.setOnClickListener {
            if (npFrom.value == npFrom.minValue && npTo.value == npTo.minValue) {
                vm.updateYearRange(from = -1, to = -1)
            } else if (npFrom.value == npFrom.minValue && npTo.value != npTo.minValue) {
                vm.updateYearRange(from = -1, to = npTo.value)
            } else if (npFrom.value != npFrom.minValue && npTo.value == npTo.minValue) {
                vm.updateYearRange(from = npFrom.value, to = -1)
            } else {
                vm.updateYearRange(from = npFrom.value, to = npTo.value)
            }
            d.dismiss()
        }

        d.show()

        val editViewNpTo = npTo.getChildAt(0)
        if (editViewNpTo is EditText) {
            editViewNpTo.setFilters(emptyArray())
        }

        val editViewNpFrom = npFrom.getChildAt(0)
        if (editViewNpFrom is EditText) {
            editViewNpFrom.setFilters(emptyArray())
        }

    }

    private fun showCountryDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val selectText = resources.getString(R.string.fragment_movie_search_filter_country_select_select)
        val titleText = resources.getString(R.string.fragment_movie_search_filter_country_select_title)
        builder
            .setNeutralButton("Сбросить") { _, _ ->
                vm.uncheckAllSelectedCountries()
            }
            .setTitle(titleText)
            .setPositiveButton(selectText) { _, _ -> }
            .setMultiChoiceItems(countriesArray, checkedCountriesArray) { _, which, isChecked ->
                vm.updateSelectedCountries(index = which, state = isChecked)
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun listenAgeRating() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.ageRatingRangeStateFlow().collect {
                    binding.apply {
                        if (it.from == ageRatingRangeSlider.valueFrom.toInt() && it.to == ageRatingRangeSlider.valueTo.toInt()) {
                            ageRating.text = resources.getString(R.string.fragment_movie_search_filter_age_rating_any)
                        } else if (it.from == it.to) {
                            ageRating.text = "${it.from}+"
                        } else if (it.from == ageRatingRangeSlider.valueFrom.toInt() && it.to != ageRatingRangeSlider.valueTo.toInt()) {
                            ageRating.text = resources.getString(R.string.fragment_movie_search_filter_to2) + " ${it.to}"
                        } else if (it.from != ageRatingRangeSlider.valueFrom.toInt() && it.to == ageRatingRangeSlider.valueTo.toInt()) {
                            ageRating.text = resources.getString(R.string.fragment_movie_search_filter_from2) + " ${it.from}"
                        } else {
                            ageRating.text = resources.getString(R.string.fragment_movie_search_filter_from2) + " ${it.from} " + resources.getString(R.string.fragment_movie_search_filter_to2) + " ${it.to}"
                        }
                    }
                }
            }
        }
    }

    private fun listenYear() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.yearRangeStateFlow().collect {
                    selectedYears = it
                    var text = ""

                    if (it.from == -1 && it.to == -1) {
                        text = resources.getString(R.string.fragment_movie_search_filter_any)
                    } else if (it.from != -1 && it.to == -1) {
                        text = resources.getString(R.string.fragment_movie_search_filter_from) + " ${it.from}"
                    } else if (it.from == -1 && it.to != -1) {
                        text = resources.getString(R.string.fragment_movie_search_filter_to) + " ${it.to}"
                    } else {
                        text = resources.getString(R.string.fragment_movie_search_filter_from) + " ${it.from} " + resources.getString(R.string.fragment_movie_search_filter_to) + " ${it.to}"
                    }
                    binding.years.text = text
                }
            }
        }
    }

    private fun listenLoadingCountry() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.countryRangeStateFlow().collect {
                    binding.apply {
                        when (it) {
                            is LoadingCountryFilterUiState.Success -> {
                                loadingIndicator.visibility = View.GONE
                                content.visibility = View.VISIBLE
                                errorMassage.visibility = View.GONE

                                val tmpCountriesArray = mutableListOf<String>()
                                val tmpCheckedCountriesArray = mutableListOf<Boolean>()
                                var tmpCountriesString = ""
                                it.countries.forEach {
                                    tmpCountriesArray.add(it.name)
                                    tmpCheckedCountriesArray.add(it.isSelected)
                                    if (it.isSelected) {
                                        tmpCountriesString += it.name + ", "
                                    }
                                }
                                if (tmpCountriesString == "") {
                                    tmpCountriesString = resources.getString(R.string.fragment_movie_search_filter_any_country)
                                } else {
                                    tmpCountriesString = tmpCountriesString.dropLast(2)
                                }

                                countries.text = tmpCountriesString
                                countriesArray = tmpCountriesArray.toTypedArray()
                                checkedCountriesArray = tmpCheckedCountriesArray.toBooleanArray()
                            }

                            is LoadingCountryFilterUiState.Error -> {
                                loadingIndicator.visibility = View.GONE
                                content.visibility = View.INVISIBLE
                                errorMassage.visibility = View.VISIBLE
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), it.massage, Toast.LENGTH_SHORT).show()
                                }
                            }

                            is LoadingCountryFilterUiState.Loading -> {
                                loadingIndicator.visibility = View.VISIBLE
                                content.visibility = View.INVISIBLE
                                errorMassage.visibility = View.GONE

                            }
                        }
                    }
                }
            }
        }
    }
}