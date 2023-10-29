package com.biybiruza.musicapp.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.biybiruza.musicapp.R

open class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    val navOptionAnim = NavOptions.Builder()
        .setEnterAnim(R.anim.set_enter_anim)
        .setExitAnim(R.anim.set_exit_anim)
        .setPopEnterAnim(R.anim.set_enter_anim_left_to_right)
        .setPopExitAnim(R.anim.set_exit_anim_left_to_right).build()


}