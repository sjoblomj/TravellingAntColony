package org.sjoblomj.travellingantcolony.domain

data class Place(val x: Double, val y: Double, private val name: String = "") {

  override fun toString() = if (name != "") name else "[$x, $y]"
}
