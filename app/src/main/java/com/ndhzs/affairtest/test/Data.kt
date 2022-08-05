package com.ndhzs.affairtest.test

/**
 * ...
 * @author 985892345 (Guo Xiangrui)
 * @email guo985892345@foxmail.com
 * @date 2022/8/5 16:50
 */
sealed interface Data {
  abstract override fun equals(other: Any?): Boolean
  val onlyId: Any
}

data class Week(
  val week: String,
  val times: List<Time>,
  var isWrapBefore: Boolean = false
) : Data {
  override val onlyId: Any
    get() = week
  
  var isFold: Boolean = true
  
  var oldLayoutPosition: Int = -1
}

data class Time(
  val week: String,
  val time: String,
  var isWrapBefore: Boolean = false
) : Data {
  override val onlyId: Any
    get() = time
}