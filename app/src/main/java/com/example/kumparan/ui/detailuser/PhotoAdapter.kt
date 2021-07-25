package com.example.kumparan.ui.detailuser

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kumparan.R
import com.example.kumparan.data.remote.model.PhotoDTO
import com.example.kumparan.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_photos.*

class PhotoAdapter(private val onClick: (PhotoDTO) -> Unit) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var items: List<PhotoDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_photos))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onClick(items[adapterPosition])
            }
        }

        fun bind(item: PhotoDTO) {
            photoAlbum.load(item.thumbnailUrl)
        }
    }
}

