package com.ndhzs.affairtest.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.ndhzs.affairtest.R

/**
 * ...
 * @author 985892345 (Guo Xiangrui)
 * @email guo985892345@foxmail.com
 * @date 2022/8/5 16:58
 */
class Adapter : ListAdapter<Data, Adapter.VH>(
  object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
      return if (oldItem::class.hashCode() != newItem::class.hashCode()) {
        false
      } else oldItem.onlyId == newItem.onlyId
    }
  
    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
      return oldItem == newItem
    }
  
    override fun getChangePayload(oldItem: Data, newItem: Data): Any {
      return "" // 重写这个方法可以在刷新时不使 ViewHolder 互换
    }
  }
) {
  
  sealed class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
  
  inner class WeekVH(itemView: View) : VH(itemView) {
    val tvWeek: TextView = itemView.findViewById(R.id.tv_week)
    
    init {
      tvWeek.setOnClickListener {
        val data = getItem(layoutPosition) as Week
        data.isFold = !data.isFold // 这里只需要修改折叠状态即可
        submitList(AffairDataUtils.getNewList(currentList))
      }
    }
  }
  
  inner class TimeVH(itemView: View) : VH(itemView) {
    val tvTime: TextView = itemView.findViewById(R.id.tv_time)
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val inflater = LayoutInflater.from(parent.context)
    return when (viewType) {
      WeekVH::class.hashCode() -> WeekVH(inflater.inflate(R.layout.layout_week, parent, false))
      TimeVH::class.hashCode() -> TimeVH(inflater.inflate(R.layout.layout_time, parent, false))
      else -> error("???")
    }
  }
  
  override fun onBindViewHolder(holder: VH, position: Int) {
    val data = getItem(position)
    val lp = holder.itemView.layoutParams as FlexboxLayoutManager.LayoutParams
    when (holder) {
      is WeekVH -> {
        data as Week
        holder.tvWeek.text = data.week
        lp.isWrapBefore = data.isWrapBefore
      }
      is TimeVH -> {
        data as Time
        holder.tvTime.text = data.time
        lp.isWrapBefore = data.isWrapBefore
      }
    }
  }
  
  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is Week -> WeekVH::class.hashCode()
      is Time -> TimeVH::class.hashCode()
    }
  }
}