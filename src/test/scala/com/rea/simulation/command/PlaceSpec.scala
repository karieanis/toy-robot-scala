package com.rea.simulation.command

import com.rea.simulation.model.{Orientation, Point}
import com.rea.simulation.model.mutable.{Robot, World}
import org.scalatest.{FlatSpec, Matchers}

class PlaceSpec extends FlatSpec with Matchers {
  "A request to place a robot outside the bounds of the world" should "be ignored" in {
    val world = World(1,1)

    Command.place(Point(1,1), Orientation.NORTH).execute(world)
    world.robot should equal (None)
  }

  "A request to place a robot within the bounds of the world" should "be successful" in {
    val world = World(1,1)

    Command.place(Point(0,0), Orientation.NORTH).execute(world)
    world.robot should not equal None // robot is no longer none
    world.robot.get.orientation should equal (Orientation.NORTH)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )
  }

  "A request to place an existing robot within the bounds of the world" should "be successful" in {
    val world = World(2,2)

    Command.place(Point(0,0), Orientation.NORTH).execute(world)
    world.robot should not equal None // robot is no longer none
    world.robot.get.orientation should equal (Orientation.NORTH)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )

    Command.place(Point(1,1), Orientation.EAST).execute(world)
    world.robot should not equal None // robot is no longer none
    world.robot.get.orientation should equal (Orientation.EAST)
    world.robot.get.location should have (
      'x (1),
      'y (1)
    )
  }

  "A request to place an existing robot outside the bounds of the world" should "be ignored" in {
    val world = World(1,1)

    Command.place(Point(0,0), Orientation.NORTH).execute(world)
    world.robot should not equal None // robot is no longer None
    world.robot.get.orientation should equal (Orientation.NORTH)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )

    Command.place(Point(0,1), Orientation.NORTH).execute(world) // attempt to place outside of the bounds

    // robot should be unchanged
    world.robot.get.orientation should equal (Orientation.NORTH)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )
  }
}
