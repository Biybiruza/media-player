package com.biybiruza.musicapp.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.data.SongData
import com.biybiruza.musicapp.data.SongDataUrl
import com.biybiruza.musicapp.databinding.FragmentMusicListBinding
import com.biybiruza.musicapp.ui.base.BaseFragment

class MusicListFragment : BaseFragment(R.layout.fragment_music_list) {

    private lateinit var binding: FragmentMusicListBinding
    private lateinit var adapterMusicList: AdapterMusicList
    private lateinit var adapterMusicListUrl: AdapterMusicListUrl
    private lateinit var navController: NavController
    private var songsList = listOf<SongData>()
    private var title = ""

    var list = listOf<String>(
        "https://uzhits.net/uploads/files/2023-10/jaloliddin-ahmadaliyev-dil-ekan_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-06/sardor-safarov-yuragim-bom-bosh_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-07/akbarbek-sapayev-xoshroy_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-07/jaloliddin-ahmadaliyev-kelganmikan_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-07/husan-korolmaslar_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-07/jasur-umirov-ulugbek-yulchiyev-menga-arzon-kapalaklar-yoqmaydi_(uzhits.net).mp3",
        "https://uzhits.net/uploads/files/2023-09/mirjalol-nematov-roza_(uzhits.net).mp3"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicListBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(), R.id.navGraph_activity)
        adapterMusicList = AdapterMusicList()
        adapterMusicListUrl = AdapterMusicListUrl()
        title = arguments?.getString("title") ?: ""

        /*check type data*/
        if (title == "musics in app") {
            songsList = loadData()
            binding.rvMusics.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapterMusicList.list = songsList
            binding.rvMusics.adapter = adapterMusicList

        } else if (title == "Online musics") {
            val list: List<SongDataUrl> = loadDataWithUrl()
            binding.rvMusics.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapterMusicListUrl.list = list
            binding.rvMusics.adapter = adapterMusicListUrl
        }


        /*back btn*/
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        /*action is item Clicked*/
        adapterMusicList.setOnItemClickListener { model, position ->
            navController.navigate(
                R.id.action_mainFragment_to_musicPlayerFragment,
                bundleOf(Pair("title", title), Pair("position", position)),
                navOptionAnim
            )
        }

        /*action is item Clicked in online music*/
        adapterMusicListUrl.setOnItemClickListener { model, position ->
            navController.navigate(
                R.id.action_mainFragment_to_musicPlayerFragment,
                bundleOf(Pair("title", title), Pair("position", position)),
                navOptionAnim
            )
        }

    }

    fun loadData(): List<SongData> {
        val list = arrayListOf<SongData>()
        list.add(SongData("po schekam slezy", "kucher janaga", R.drawable.img_1, R.raw.song01))
        list.add(SongData("misha_marvin", "O mama", R.drawable.img_2, R.raw.song02))
        return list
    }

    fun loadDataWithUrl(): List<SongDataUrl> {
        val list = arrayListOf<SongDataUrl>()
        list.add(
            SongDataUrl(
                "Dil ekan",
                "Jaloliddin Ahmadaliyev",
                "https://yoshlar.com/_ld/184/60480512.jpg",
                "https://uzhits.net/uploads/files/2023-10/jaloliddin-ahmadaliyev-dil-ekan_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Yuragim bom bosh",
                "Sardor Safarov",
                "https://muzzona.kz/uploads/muzzona.jpg",
                "https://uzhits.net/uploads/files/2023-06/sardor-safarov-yuragim-bom-bosh_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Xoshroy",
                "Akbarbek Sapayev",
                "https://quvonch.com/uploads/quvonch.png",
                "https://uzhits.net/uploads/files/2023-07/akbarbek-sapayev-xoshroy_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Kelganmikan",
                "Jaloliddin Ahmadaliyev",
                "https://uzhits.net/templates/uzhits-261526/dleimages/no_image.jpg",
                "https://uzhits.net/uploads/files/2023-07/jaloliddin-ahmadaliyev-kelganmikan_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Korolmaslar",
                "Husan",
                "https://jozibali.uz/templates/music-hype-light/dleimages/no_image.jpg",
                "https://uzhits.net/uploads/files/2023-07/husan-korolmaslar_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Menga arzon kapalaklar yoqmaydi",
                "Jasur Umirov && Ulugbek Yulchiyev",
                "https://uzhits.net/templates/uzhits-261526/dleimages/no_image.jpg",
                "https://uzhits.net/uploads/files/2023-07/jasur-umirov-ulugbek-yulchiyev-menga-arzon-kapalaklar-yoqmaydi_(uzhits.net).mp3"
            )
        )
        list.add(
            SongDataUrl(
                "Roza",
                "Mirjalol Nematov",
                "https://n1.quvonch.com/uploads/posts/artis/mirjalol.jpg",
                "https://uzhits.net/uploads/files/2023-09/mirjalol-nematov-roza_(uzhits.net).mp3"
            )
        )
        return list
    }
}
