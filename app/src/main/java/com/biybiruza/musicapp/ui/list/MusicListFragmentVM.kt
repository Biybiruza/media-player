package com.biybiruza.musicapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData

class MusicListFragmentVM() : ViewModel() {


    val lisLiveData: MutableLiveData<List<SongData?>> = MutableLiveData()

    init {
        lisLiveData.value =lisSongs()
    }

    fun lisSongs() : List<SongData?> {
        val list = arrayListOf<SongData>()
        list.add(SongData("po schekam slezy","kucher janaga",R.drawable.img_1, R.raw.song01))
        list.add(SongData("misha_marvin","O mama",R.drawable.img_2, R.raw.song02))
        return list
    }

}