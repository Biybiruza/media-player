package com.biybiruza.musicapp.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongDataUrl
import com.biybiruza.musicapp.databinding.ItemMusicsBinding
import com.bumptech.glide.Glide

class AdapterMusicListUrl : RecyclerView.Adapter<AdapterMusicListUrl.ViewHolder>() {

    lateinit var binding: ItemMusicsBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(model: SongDataUrl, position: Int) {
            binding = ItemMusicsBinding.bind(itemView)

            binding.tvMusicName.text = model.songName
            binding.tvAuthor.text = model.songAuthor
            Glide.with(itemView).load(model.songImg).into(binding.ivPhoto)

            itemView.setOnClickListener {
                onClick.invoke(model, position)
            }
        }
    }

    var list = listOf<SongDataUrl>()

    private var onClick: (model: SongDataUrl, position: Int) -> Unit = { model, position -> }

    fun setOnItemClickListener(onClick: (model: SongDataUrl, position: Int) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_musics, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.populateModel(list[position], position)
    }
}