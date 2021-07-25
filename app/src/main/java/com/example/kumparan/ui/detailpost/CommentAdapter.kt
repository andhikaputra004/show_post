package com.example.kumparan.ui.detailpost

import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.example.kumparan.R
import com.example.kumparan.data.remote.model.CommentDTO
import com.example.kumparan.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_comment.*

class CommentAdapter :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var items: List<CommentDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_comment))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: CommentDTO) {
            textComment.text = setTextDifferentColor(item.name, item.body)
        }
    }
}

fun setTextDifferentColor(firstText: String, secondText: String) = buildSpannedString {
    bold {
        append("$firstText : ")
    }
    append(secondText)
}