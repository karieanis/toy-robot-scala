package com.rea.simulation.command

import com.rea.simulation.model.{Orientation, Point}
import com.rea.simulation.model.mutable.{Robot, World}
import org.scalatest.{FlatSpec, Matchers}

class MoveSpec extends FlatSpec with Matchers {
  val command: Command = Command.move()

  "A request to move" should "have no affect when a robot has not been placed in the world" in {
    val world = World(1,1)
    command.execute(world)
    world.robot should equal (None)
  }

  "A request to move outside the bounds of the world on the y axis" should "be ignored" in {
    val world = World(1, 1, Robot(Point(0, 0), Orientation.SOUTH))
    command.execute(world)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )
  }

  "A request to move within the bounds of the world on the y axis" should "be successful" in {
    val world = World(2, 2, Robot(Point(0, 0), Orientation.NORTH))
    command.execute(world)
    world.robot.get.location should have (
      'x (0),
      'y (1)
    )
  }

  "A request to move outside the bounds of the world on the x axis" should "be ignored" in {
    val world = World(1, 1, Robot(Point(0, 0), Orientation.WEST))
    command.execute(world)
    world.robot.get.location should have (
      'x (0),
      'y (0)
    )
  }

  "A request to move within the bounds of the world on the x axis" should "be successful" in {
    val world = World(2, 2, Robot(Point(0, 0), Orientation.EAST))
    command.execute(world)
    world.robot.get.location should have (
      'x (1),
      'y (0)
    )
  }
}
