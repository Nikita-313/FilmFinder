package com.cinetech.filmfinder.presentation.screens.movie

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.cinetech.domain.models.Comment
import com.cinetech.filmfinder.R
import com.cinetech.filmfinder.databinding.ItemCommentBinding
import com.cinetech.filmfinder.presentation.helpers.toPx
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.BaseHolder>() {


    var items: List<Comment> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    var loadingIndicatorCount = 1
        set(newValue) {
            if (field > newValue) {
                notifyItemRemoved(items.size)
                field = newValue
            } else if (field < newValue) {
                notifyItemInserted(items.size)
                field = newValue
            }
        }
    var loadingIndicator: Boolean = false
        set(newValue) {
            field = newValue
            notifyItemChanged(items.size)
        }

    override fun getItemCount(): Int = items.size + loadingIndicatorCount

    override fun getItemViewType(position: Int) = when {
        position >= items.size -> TYPE_LOADING_INDICATOR
        else -> TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder = when (viewType) {
        TYPE_ITEM -> {
            val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val layoutParams = LinearLayout.LayoutParams(
                (parent as RecyclerView).layoutManager!!.width - parent.context.toPx(80).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.marginStart = parent.context.toPx(20).toInt()
            binding.root.setLayoutParams(layoutParams)

            ItemHolder(binding)
        }

        TYPE_LOADING_INDICATOR -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_load_indocator, parent, false)
            LoadingHoled(view)
        }

        else -> throw RuntimeException("Item type: $viewType. Not supported")
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        when (holder) {
            is ItemHolder -> {
                holder.binding.apply {
                    val comment = items[position]
                    comment.author?.let { userName.text = it }
                    comment.date?.let { dateCreate.text = getFormattedDate(it) }
                    comment.title?.let { commentTitle.text = it }

                    comment.review?.let {
                        if (it.length > 250) {
                            commentBody.text = Html.fromHtml(it.substring(0, 250), Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            commentBody.text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
                        }
                    }
                }
            }

            is LoadingHoled -> {
                if (loadingIndicator) {
                    holder.progressIndicator.visibility = View.VISIBLE
                } else {
                    holder.progressIndicator.visibility = View.GONE
                }
            }

            else -> {}
        }
    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemHolder(val binding: ItemCommentBinding) : BaseHolder(binding.root)

    class LoadingHoled(view: View) : BaseHolder(view) {
        val progressIndicator: ProgressBar = view.findViewById(R.id.progressIndicator)
    }

    private fun getFormattedDate(strDate: String): String {
        val formatter: DateFormat = SimpleDateFormat(SERVER_FORMAT, Locale("ru", "RU"))
        val date: Date? = formatter.parse(strDate)
        val fmtOut = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
        return fmtOut.format(date)
    }


    companion object {
        const val SERVER_FORMAT = "yyyy-MM-d'T'H:mm:ss.SSS'Z'"
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING_INDICATOR = 2

    }
}