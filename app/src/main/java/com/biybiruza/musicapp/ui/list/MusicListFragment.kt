package com.biybiruza.musicapp.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData
import com.biybiruza.musicapp.databinding.FragmentMusicListBinding
import com.biybiruza.musicapp.ui.base.BaseFragment

class MusicListFragment : BaseFragment(R.layout.fragment_music_list) {

    private lateinit var binding: FragmentMusicListBinding
    private lateinit var adapterMusicList: AdapterMusicList
    private lateinit var navController: NavController
    private var songsList = listOf<SongData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicListBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.navGraph_activity)
        adapterMusicList = AdapterMusicList()
        binding.tvTitle.text = arguments?.getString("title") ?: ""



        songsList = loadData()
        binding.rvMusics.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        adapterMusicList.list = songsList
        binding.rvMusics.adapter = adapterMusicList

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        adapterMusicList.setOnItemClickListener {model,position->
            navController.navigate(R.id.action_mainFragment_to_musicPlayerFragment,
                bundleOf(Pair("model",model),Pair("position",position)),
                navOptionAnim
            )
        }

    }

    fun loadData() : List<SongData> {
        val list = arrayListOf<SongData>()
        list.add(SongData("po schekam slezy", "kucher janaga", R.drawable.img_1, R.raw.song01))
        list.add(SongData("misha_marvin", "O mama", R.drawable.img_2, R.raw.song02))
        return list
    }

    private val songObsever = Observer<List<SongData>> {
        adapterMusicList.list = it
    }
}
