package com.some.mvvmdemo.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author xiangxing
 */
class ViewPagerFragmentAdapter: FragmentStateAdapter {

    var fragments: MutableList<Fragment>?= null

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    constructor(fragmentActivity: FragmentActivity, fragments: MutableList<Fragment>) : super
    (fragmentActivity){
        this.fragments = fragments
    }




    override fun getItemCount(): Int {

        return fragments!!.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments!!.get(position)
    }
}