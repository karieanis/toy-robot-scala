package com.rea.simulation.model

import com.rea.simulation.model.mutable.World

/**
  * Simple case class representing a point within a two dimensional space
  *
  * @param x  A x position
  * @param y  A y position
  */
case class Point(x: Int, y: Int) {
  /**
    * Does this point exist within the bounds of the passed world?
    *
    * @param world  A world
    * @return       true if the point exists within the bounds of the world, false otherwise
    */
  def within(world:World): Boolean = {
    world.xAxisBounds.contains(x) && world.yAxisBounds.contains(y)
  }
}
