package com.wangkeke.kotlinnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_tab_layout.*
import java.util.ArrayList

/**
 * tabLayout kotlin使用
 */
class TabLayoutActivity : AppCompatActivity() {

    private val tabAll= arrayOf("福利","Android","IOS","休息视频","拓展资源","前端","全部")

    var tabFragments:java.util.ArrayList<Fragment>? =null

    var tabIndicators:java.util.ArrayList<String> ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)

        initContent()
        initTab()
    }

    private fun initTab() {

        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.tabTextColors = ContextCompat.getColorStateList(this,android.R.color.white)
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, android.R.color.white))
        tabLayout.elevation = 10f
        tabLayout.setupWithViewPager(vp_content)

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_content.setCurrentItem(tab!!.position,false)
            }


        })

        val contentAdapter = ContentPagerAdapter(supportFragmentManager)
        vp_content.adapter = contentAdapter
    }

    private fun initContent() {

        tabIndicators = ArrayList()

        for (i in tabAll){
            tabIndicators!!.add(i)
        }

        tabFragments = java.util.ArrayList()

        for (item in tabIndicators!!)
        {
            tabFragments!!.add(TabContentFragment.newInstance(item,item))
        }


    }

    inner class ContentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return tabFragments!![position]
        }

        override fun getCount(): Int {
            return tabFragments!!.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators!![position]
        }

    }

}
