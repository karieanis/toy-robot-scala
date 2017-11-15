package com.rea.simulation.command

import com.rea.simulation.model.Direction
import com.rea.simulation.model.mutable.World
import org.scalatest.{FlatSpec, Matchers}

/**
  * Basic turn specification tests
  */
class TurnSpec extends FlatSpec with Matchers {
  "A request to turn" should "have no affect when a robot has not been placed in the world" in {
    val world = World(1,1)
    Command.turn(Direction.LEFT).execute(world)
    world.robot should equal (None)
  }
}