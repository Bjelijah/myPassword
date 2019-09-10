package xyz.mercs.libcommon.base.mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class BasePagerAdapter(fm: FragmentManager?, var list:List<Fragment>): FragmentPagerAdapter(fm) {
    var mTitle :Array<String>?=null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItem(pos: Int): Fragment = list[pos]
    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        if (mTitle==null)return null
        if (position>mTitle?.size?:0)return null
        return mTitle!![position]
    }
}