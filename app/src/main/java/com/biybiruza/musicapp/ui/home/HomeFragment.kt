package com.biybiruza.musicapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.databinding.FragmentHomeBinding
import com.biybiruza.musicapp.ui.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        onClicked(binding.folderApp,binding.tvMusicApp.text)
        onClicked(binding.folderOnline,binding.tvMusicOnline.text)

        /*binding.folderApp.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_musicListFragment, bundleOf(
                    Pair("title", binding.tvMusicApp.text)
                ),
                navOptionAnim
            )
        }

        binding.folderOnline.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_musicListFragment, bundleOf(
                    Pair("title", binding.tvMusicOnline.text)
                ),
                navOptionAnim
            )
        }*/

        onClick(binding.folderPhone)

    }

    private fun onClicked(folder: FrameLayout,title:CharSequence){
        folder.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_musicListFragment, bundleOf(
                    Pair("title", title)
                ),
                navOptionAnim
            )
        }
    }

    private fun onClick(folder: FrameLayout) {
        folder.setOnClickListener {
            Toast.makeText(requireActivity(),"Hali bu tugma ulanmadi", Toast.LENGTH_SHORT).show()
        }
    }
}