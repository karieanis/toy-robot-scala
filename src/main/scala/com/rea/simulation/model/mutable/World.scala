package com.rea.simulation.model.mutable

object World {
  def apply(xAxisUpperBound: Int, yAxisUpperBound: Int): World =  World(xAxisUpperBound, yAxisUpperBound, null)
  def apply(xAxisUpperBound: Int, yAxisUpperBound: Int, robot: Robot): World =  new World(Range(0, xAxisUpperBound), Range(0, yAxisUpperBound), Option(robot))
}

/**
  * Simulation world model. X and Y bounds are immutable, the existence of a robot within the world is subject to change.
  *
  * @param xAxisBounds  A contiguous range, representing all possible values on the x axis
  * @param yAxisBounds  A contiguous range, representing all possible values on the y axis
  * @param robot        An optional robot
  */
class World(val xAxisBounds: Range, val yAxisBounds: Range, var robot: Option[Robot])