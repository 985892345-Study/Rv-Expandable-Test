package com.ndhzs.affairtest.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.ndhzs.affairtest.R
import java.util.*

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
  }
) {
  
  sealed class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
  
  inner class WeekVH(itemView: View) : VH(itemView) {
    val tvWeek: TextView = itemView.findViewById(R.id.tv_week)
    
    init {
      tvWeek.setOnClickListener {
        val data = getItem(layoutPosition) as Week
        if (data.isFold) {
          data.isFold = false
          data.oldLayoutPosition = layoutPosition
          val newList = currentList.toMutableList()
          if (layoutPosition + 1 < newList.size) {
            if (newList[layoutPosition + 1] is Week) {
              val old = newList.removeAt(layoutPosition + 1)
              newList.add(layoutPosition + 1, (old as Week).copy(isWrapBefore = true))
            }
          }
          newList.removeAt(layoutPosition)
          newList.add(0, data)
          newList.addAll(1, data.times)
          if (newList.size > 2) {
            if (newList[1] is Time) {
              val old = newList.removeAt(1)
              newList.add(1, (old as Time).copy(isWrapBefore = true))
            }
          }
          submitList(newList)
        } else {
          data.isFold = true
          val newList = currentList.toMutableList()
          newList.removeAll(data.times)
          newList.removeAt(0)
          newList.add(data.oldLayoutPosition, data.copy(isWrapBefore = false))
          data.oldLayoutPosition = -1
          submitList(newList)
        }
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