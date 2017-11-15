package com.rea.simulation.command

import com.rea.simulation.model.{Direction, Orientation, Point}
import com.rea.simulation.model.mutable.{Robot, World}
import org.scalatest.{FunSuite, Matchers}

/**
  * Tests all combinations of turning possible
  */
class TurnSuite extends FunSuite with Matchers {
  val left: Command  = Command.turn(Direction.LEFT)
  val right: Command = Command.turn(Direction.RIGHT)
  
  val combinations = Seq(
    (Robot(Point(0, 0), Orientation.NORTH), left, Orientation.WEST),
    (Robot(Point(0, 0), Orientation.WEST),  left, Orientation.SOUTH),
    (Robot(Point(0, 0), Orientation.SOUTH), left, Orientation.EAST),
    (Robot(Point(0, 0), Orientation.EAST),  left, Orientation.NORTH),

    (Robot(Point(0, 0), Orientation.NORTH), right, Orientation.EAST),
    (Robot(Point(0, 0), Orientation.EAST),  right, Orientation.SOUTH),
    (Robot(Point(0, 0), Orientation.SOUTH), right, Orientation.WEST),
    (Robot(Point(0, 0), Orientation.WEST),  right, Orientation.NORTH)
  )

  combinations.foreach {
    case (robot: Robot, command: Command, expected: Orientation) =>
      test(s"A turn command $command when facing ${robot.orientation} should result in a new orientation of $expected") {
        val world = World(1, 1, robot)
        command.execute(world)
        world.robot.get.orientation shouldBe expected
      }
  }
}
