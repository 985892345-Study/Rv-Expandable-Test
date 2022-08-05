package com.ndhzs.affairtest.test

/**
 * ...
 *
 * @author 985892345 (Guo Xiangrui)
 * @email 2767465918@qq.com
 * @date 2022/8/5 22:27
 */
object AffairDataUtils {
  
  /**
   * [Week.isWrapBefore] 由自身的 [Week.isFold] 和 前一个 是否是 [Time] 来决定
   *
   * ```
   *                   isFold
   * isWrapBefore     T      F
   *
   *  prev     T      T      T
   *   is
   *  Time     F      F      T
   *
   *  ```
   */
  fun getNewList(list: List<Data>): List<Data> {
    val newList = arrayListOf<Data>()
    var index = 0
    while (index < list.size) {
      val prev = newList.lastOrNull()
      val next = list.getOrNull(index + 1)
      when (val now = list[index]) {
        is Week -> {
          if (prev is Time || !now.isFold) {
            // 这个时候 isWrapBefore 该为 true
            newList.add(if (now.isWrapBefore) now else now.copy(isWrapBefore = true))
          } else {
            // 这个时候 isWrapBefore 该为 false
            newList.add(if (!now.isWrapBefore) now else now.copy(isWrapBefore = false))
          }
          if (now.isFold) {
            // 如果打算折叠
            if (next is Time) {
              // 但 next 却是 Time，就往后找到最后一个 Time 的 index
              while (list.getOrNull(index + 2) is Time) {
                index++
              }
              index++ // 目前这个 index 就是最后一个 Time
            }
          } else {
            // 如果打算展开
            if (next is Week || next == null) {
              // 但 next 却是 Week 或者 null，需要把后面的 Time 加到 newList 中
              if (now.times.isNotEmpty()) {
                val first = now.times[0]
                first.isWrapBefore = true // 第一个 Time 需要换行
                newList.addAll(now.times)
              }
            }
          }
        }
        is Time -> {
          newList.add(now) // 自身是 Time 直接加即可，因为在 Week 中已判断完逻辑
        }
      }
      index++
    }
    return newList
  }
}