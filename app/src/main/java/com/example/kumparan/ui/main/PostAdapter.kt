package com.example.kumparan.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kumparan.R
import com.example.kumparan.data.remote.model.PostDTO
import com.example.kumparan.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*

class PostAdapter(private val selectedPost: (PostDTO) -> Unit) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var items: List<PostDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_post))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                selectedPost(items[adapterPosition])
            }
        }

        fun bind(item: PostDTO) {
            textTitle.text = item.title
            textBody.text = item.body
            textName.text = item.name
            textCompany.text = "Company : ${item.company}"
        }
    }
}