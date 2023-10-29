package com.biybiruza.musicapp.ui.player

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData
import com.biybiruza.musicapp.databinding.FragmentMusicPlayerBinding
import com.biybiruza.musicapp.ui.list.MusicListFragment
import java.text.SimpleDateFormat


class MusicPlayerFragment : Fragment(R.layout.fragment_music_player) {

    private lateinit var binding: FragmentMusicPlayerBinding
    private lateinit var list: List<SongData>
    private var position = 0
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicPlayerBinding.bind(view)
        list = MusicListFragment().loadData()
        position = arguments?.getInt("position", 0)!!
        mediaPlayer = MediaPlayer()
        mediaPlayer = MediaPlayer.create(requireActivity(), list[position].songRaw)
        handler = Handler(Looper.getMainLooper())


//        val attr: BasicFileAttributes = Files.readAttributes(Path(getString(model.songRaw)), BasicFileAttributes::class.java)

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.apply {

            loadInfoAudio()

            btnPlay.setOnClickListener {
                play()
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

                if (position < list.size) {
                    position++
                    changeAudio()
                } else if (position == list.size) {
                    Toast.makeText(requireActivity(), "this is last music", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            btnPrevious.setOnClickListener {

                if (position > 0) {
                    position--
                    changeAudio()
                } else if (position == -1) {
                    Toast.makeText(requireActivity(), "this is first music", Toast.LENGTH_SHORT)
                        .show()
                }
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
            if (position < list.size) {
                position++
                changeAudio()
            } else if (position == list.size) {
                Toast.makeText(
                    requireActivity(),
                    "this is last music",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun changeAudio() {
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(requireActivity(), list[position].songRaw)
        mediaPlayer.start()
        handler.postDelayed(runnable, 100)
        binding.btnPlay.setImageResource(R.drawable.ic_pause)
        loadInfoAudio()
    }

    private fun loadInfoAudio() {
        binding.apply {
            tvMusicName.text = list[position].songName
            tvAuthor.text = list[position].songAuthor
            ivPhoto.setImageResource(list[position].songImg)
            val dateFormat = SimpleDateFormat("mm:ss")
            tvEndTime.text = dateFormat.format(mediaPlayer.duration)
        }
    }

    private fun play() {

        val songDuration = mediaPlayer.duration
        binding.seekBar.max = songDuration
        handler.postDelayed(runnable, 100)


        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            binding.btnPlay.setImageResource(R.drawable.ic_play)
        } else {
            mediaPlayer.start()
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
                handler.postDelayed(this, 100)
            }
        }
    }

}
