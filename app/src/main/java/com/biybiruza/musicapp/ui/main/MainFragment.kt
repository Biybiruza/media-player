package com.biybiruza.musicapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.biybiruza.musicapp.R
import com.biybiruza.musicapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        navController = Navigation.findNavController(requireActivity(),R.id.main_fragment)

        binding.apply {
            bottomMain.setupWithNavController(navController)
        }
    }
}