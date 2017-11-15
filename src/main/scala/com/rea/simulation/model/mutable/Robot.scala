package com.rea.simulation.model.mutable

import com.rea.simulation.model.{Orientation, Point}

/**
  * Mutable robot entity representation.
  *
  * @param location     A point within a two dimensional space, representing the current location of the robot
  * @param orientation  An orientation for the robot within two dimensional space
  */
case class Robot(var location: Point, var orientation: Orientation)
