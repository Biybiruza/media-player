package com.biybiruza.musicapp.data

import java.io.Serializable

data class SongData (
    val songName: String,
    val songAuthor: String,
    val songImg: Int,
    val songRaw: Int,
):Serializable