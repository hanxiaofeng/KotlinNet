package com.wangkeke.kotlinnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import com.wangkeke.kotlinnet.fragment.*
import kotlinx.android.synthetic.main.activity_tab_layout.*
import java.util.ArrayList

/**
 * tabLayout kotlin使用
 */
class TabLayoutActivity : AppCompatActivity() {

    private val tabAll= arrayOf("福利","Anko-Dialogs","sqlite","intent-log","Anko-Layout")

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
                vp_content.setCurrentItem(tab!!.position,true)
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

        for (i in tabIndicators!!.indices)
        {
            when (i) {
                0 -> tabFragments!!.add(GirlFragment.newInstance(""))
                1 -> tabFragments!!.add(AnkoDialogFragment.newInstance(""))
                2 -> tabFragments!!.add(SqliteFragment.newInstance(""))
                3 -> tabFragments!!.add(IntentLogFragment.newInstance(""))
                4 -> tabFragments!!.add(AnkoLayoutFragment.newInstance(""))
                else -> tabFragments!!.add(TabContentFragment.newInstance(tabIndicators!![i],tabIndicators!![i]))
            }
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
