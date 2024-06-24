package com.zhaiker.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTiktokBinding

class TiktokActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTiktokBinding>(this,
            R.layout.activity_tiktok
        ).apply {
            recyclerview.adapter = TiktokAdapter()
            val layoutManager = LinearLayoutManager(this@TiktokActivity,LinearLayoutManager.VERTICAL,false)
            recyclerview.layoutManager = layoutManager
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(recyclerview)
            recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var curPage = -1
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val snapView = pagerSnapHelper.findSnapView(layoutManager)
                        snapView?.let {
                            val position = layoutManager.getPosition(it)
                            if (curPage != position) {
                                curPage = position
                                Log.d("chenyu", "curPage=$curPage")
                            }
                        }

                    }


                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    if (layoutManager.childCount > 0
                            && lastVisiblePosition >= layoutManager.itemCount - 1
                            && layoutManager.itemCount > layoutManager.childCount) {
                        (recyclerview.adapter as TiktokAdapter).run {
                            count += 10
                            notifyDataSetChanged()
                        }
                    }


                }
            })

        }


    }
}