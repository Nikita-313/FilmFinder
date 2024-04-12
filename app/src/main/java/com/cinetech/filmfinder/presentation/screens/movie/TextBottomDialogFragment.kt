package com.cinetech.filmfinder.presentation.screens.movie

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cinetech.filmfinder.databinding.FragmentTextBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TextBottomDialogFragment(
    private val title: String?,
    private val body: String,
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTextBottomDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTextBottomDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(title == null) {
            binding.title.visibility = View.GONE
        } else {
            binding.title.text = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY)
        }

        binding.body.text = Html.fromHtml(body, Html.FROM_HTML_MODE_LEGACY)

        binding.close.setOnClickListener {
            dismiss()
        }
    }
}