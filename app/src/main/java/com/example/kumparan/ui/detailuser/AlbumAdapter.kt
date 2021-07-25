package com.example.kumparan.ui.detailuser

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kumparan.R
import com.example.kumparan.data.remote.model.AlbumDTO
import com.example.kumparan.data.remote.model.PhotoDTO
import com.example.kumparan.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_album.*

class AlbumAdapter(private val onClick: (PhotoDTO) -> Unit) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    var items: List<AlbumDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_album))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: AlbumDTO) {
            textTitle.text = item.title
            val photoAdapter = PhotoAdapter(onClick)
            recyclerPhoto.layoutManager =
                GridLayoutManager(containerView.context, 2, GridLayoutManager.VERTICAL, false)
            recyclerPhoto.adapter = photoAdapter
            photoAdapter.items = item.listPhoto

        }
    }
}