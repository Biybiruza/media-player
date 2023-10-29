package com.biybiruza.musicapp.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData
import com.biybiruza.musicapp.databinding.ItemMusicsBinding

class AdapterMusicList : RecyclerView.Adapter<AdapterMusicList.ViewHolder>() {

    lateinit var binding: ItemMusicsBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populateModel(model: SongData, position: Int) {
            binding = ItemMusicsBinding.bind(itemView)

            binding.tvMusicName.text = model.songName
            binding.tvAuthor.text = model.songAuthor
            binding.ivPhoto.setImageResource(model.songImg)

            itemView.setOnClickListener {
                onClick.invoke(model,position)
            }
        }
    }

    var list = listOf<SongData>()

    private var onClick: (model: SongData, position: Int) -> Unit = {model, position ->  }

    fun setOnItemClickListener(onClick: (model: SongData, position: Int) -> Unit) {
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