package com.biybiruza.musicapp.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.biybiruza.musicapp.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true)
            .setEnterAnim(R.anim.set_enter_anim)
            .setExitAnim(R.anim.set_exit_anim)
            .setPopEnterAnim(R.anim.set_enter_anim_left_to_right)
            .setPopExitAnim(R.anim.set_exit_anim_left_to_right)
            .build()

        Handler().postDelayed({
            findNavController().navigate(
                R.id.mainFragment,
                bundleOf(),
                options
            )
        }, 3000)
    }
}