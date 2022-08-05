package com.ndhzs.affairtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.ndhzs.affairtest.test.Adapter
import com.ndhzs.affairtest.test.Time
import com.ndhzs.affairtest.test.Week

class MainActivity : AppCompatActivity() {
  
  private lateinit var mRecyclerView: RecyclerView
  
  private val mAdapter: Adapter = Adapter()
  
  
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initView()
    initRecycleView()
  }
  
  private fun initView() {
    mRecyclerView = findViewById(R.id.rv)
  }
  
  private fun initRecycleView() {
    mRecyclerView.adapter = mAdapter
    
    val timeList1 = arrayListOf(
      Time("第一周", "时间1"),
      Time("第一周", "时间2"),
      Time("第一周", "时间3"),
      Time("第一周", "时间4"),
      Time("第一周", "时间5"),
      Time("第一周", "时间6")
    )
    val week1 = Week("第一周", timeList1)
  
    val timeList2 = arrayListOf(
      Time("第二周", "时间1"),
      Time("第二周", "时间2"),
    )
    val week2 = Week("第二周", timeList2)
  
    val timeList3 = arrayListOf(
      Time("第三周", "时间1"),
      Time("第三周", "时间2"),
      Time("第三周", "时间3"),
      Time("第三周", "时间4"),
    )
    val week3 = Week("第三周", timeList3)
    
    val weekList = listOf(
      week1, week2, week3
    )
    mAdapter.submitList(weekList)
    
    mRecyclerView.layoutManager = FlexboxLayoutManager(this).apply {
      alignItems = AlignItems.CENTER
      flexWrap = FlexWrap.WRAP
      flexDirection = FlexDirection.ROW
      justifyContent = JustifyContent.FLEX_START
    }
  }
}