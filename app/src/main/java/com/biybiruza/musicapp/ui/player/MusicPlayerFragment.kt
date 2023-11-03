package com.biybiruza.musicapp.ui.player

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData
import com.biybiruza.musicapp.data.SongDataUrl
import com.biybiruza.musicapp.databinding.FragmentMusicPlayerBinding
import com.biybiruza.musicapp.ui.list.MusicListFragment
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import kotlin.concurrent.timerTask


class MusicPlayerFragment : Fragment(R.layout.fragment_music_player),MediaPlayer.OnPreparedListener {

    private lateinit var binding: FragmentMusicPlayerBinding
    private lateinit var list: List<SongData>
    private var position = 0
    private var title = ""
    private lateinit var urlList2: List<String>
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler

    private var isPaused = false

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicPlayerBinding.bind(view)

        list = MusicListFragment().loadData()

        urlList2 = MusicListFragment().list

        title = arguments?.getString("title") ?: ""
        position = arguments?.getInt("position", 0)!!
        mediaPlayer = MediaPlayer()
        handler = Handler(Looper.getMainLooper())

        if (title == "musics in app") {
            mediaPlayer = MediaPlayer.create(requireActivity(), list[position].songRaw)
        } else if (title == "Online musics") {
//            mediaPlayer.setDataSource(urlList2[position])

            mediaPlayer.setOnPreparedListener(this)

        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.apply {

            loadInfoAudio()

            btnPlay.setOnClickListener {
                if (title == "musics in app") {
                    play()
                } else if (title == "Online musics") {
//                    mediaPlayer.setDataSource(urlList2[position])
//                    mediaPlayer.prepareAsync()

                    play()
//                    changeAudio()
                }

            }

            btnQuickNext.setOnClickListener {
                mediaPlayer.seekTo(mediaPlayer.currentPosition.plus(5000))
                handler.postDelayed(runnable, 5000)
            }

            btnQuickPrevious.setOnClickListener {
                mediaPlayer.seekTo(mediaPlayer.currentPosition.minus(5000))
                handler.postDelayed(runnable, 5000)
            }

            btnNext.setOnClickListener {

                if (title == "musics in app") {
                    if (position < list.size-1) {
                        position++
                        changeAudio()
                    } else if (position == list.size) {
                        Toast.makeText(requireActivity(), "this is last music", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (title == "Online musics") {
                    if (position < urlList2.size-1) {
                        position++
                        changeAudio()
                    } /*else if (position == urlList.size) {
////                        mediaPlayer2.stop()
//                        Toast.makeText(requireActivity(), "this is last music", Toast.LENGTH_SHORT)
//                            .show()
//                    }*/
                }
            }

            btnPrevious.setOnClickListener {
                mediaPlayer.stop()
                if (position > 0) {
                    position--
                    changeAudio()
                } /*else {
                    Toast.makeText(requireActivity(), "this is first music", Toast.LENGTH_SHORT)
                        .show()
                }*/
            }

            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser){
                        mediaPlayer.seekTo(progress)
                        skipToNextSong()
                    }
                    skipToNextSong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })
        }
    }

    private fun skipToNextSong() {

        if (binding.seekBar.progress == binding.seekBar.max) {
            if (title == "musics in app") {
                if (position < list.size-1) {
                    position++
                    changeAudio()
                }
            } else if (title == "Online musics") {
                if (position < urlList2.size-1) {
                    position++
                    changeAudio()
//                    play()
                }
            }
        }
    }

    private fun changeAudio() {

        mediaPlayer.reset()
        if (title == "musics in app") {
            mediaPlayer = MediaPlayer.create(requireActivity(), list[position].songRaw)
        } else if (title == "Online musics") {
            mediaPlayer.setDataSource(urlList2[position])
            mediaPlayer.prepareAsync()
        }
        mediaPlayer.start()
        handler.postDelayed(runnable, 1000)
        binding.btnPlay.setImageResource(R.drawable.ic_pause)
        loadInfoAudio()
    }

    private fun loadInfoAudio() {
        binding.apply {

            if (title == "musics in app") {
                tvMusicName.text = list[position].songName
                tvAuthor.text = list[position].songAuthor
                ivPhoto.setImageResource(list[position].songImg)
                val dateFormat = SimpleDateFormat("mm:ss")
                tvEndTime.text = dateFormat.format(mediaPlayer.duration)
            }
        }
    }

    private fun play() {

        val songDuration = mediaPlayer.duration
        binding.seekBar.max = songDuration
        handler.postDelayed(runnable, 1000)

        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            isPaused = true
            binding.btnPlay.setImageResource(R.drawable.ic_play)
        } else {
            mediaPlayer.start()
            isPaused = false
            binding.btnPlay.setImageResource(R.drawable.ic_pause)
        }
    }

    private val runnable = object : Runnable {

        override fun run() {
            val currentPosition = mediaPlayer.currentPosition
            if (currentPosition != null) {
                val dateFormat = SimpleDateFormat("mm:ss")
                binding.seekBar.progress = currentPosition
                binding.tvStartTime.text = dateFormat.format(currentPosition)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (isPaused.not()) {
            binding.progressBar.visibility = View.GONE
            mp?.start()

            val dateFormat = SimpleDateFormat("mm:ss")

            if (mp != null) {
                binding.seekBar.progress = mp.currentPosition
            }

            binding.tvStartTime.text = dateFormat.format(mp?.currentPosition)
            handler.postDelayed(runnable, 1000)

            val metadata = MediaMetadataRetriever()
            metadata.setDataSource(urlList2[position])
            binding.tvMusicName.text = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            binding.tvAuthor.text = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            binding.tvEndTime.text = dateFormat.format(mediaPlayer.duration)
            setSongImage(urlList2[position])

        } else {
            mp?.seekTo(mp.currentPosition)
        }
    }

    private fun setSongImage(filePath: String){
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)
        val art = retriever.embeddedPicture

        if (art != null){
            binding.ivPhoto.setImageBitmap(BitmapFactory.decodeByteArray(art,0,art.size))
        } else {
            binding.ivPhoto.setImageResource(R.drawable.music_player_logo)
        }
    }

}
