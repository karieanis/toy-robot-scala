package com.rea.simulation.command

import com.rea.simulation.model.{Direction, Orientation, Point}
import com.rea.simulation.model.mutable.{Robot, World}
import com.typesafe.scalalogging.LazyLogging

object Command {
  /**
    * Evaluation logic for executing a provided function given the existence of a robot. Non-existence results
    * in a warning being logged.
    */
  private object ConditionalOnRobot extends LazyLogging {
    def executeIfPresent(maybeRobot:Option[Robot])(f: => Unit): Unit = {
      maybeRobot match {
        case Some(actual) => f
        case None => logger.warn("Ignored as robot does not exist within the world")
      }
    }
  }

  /**
    * Evaluation logic for executing a provided function given a two dimensional space existing with a world.
    * Spaces outside the bounds of the world are logged as a warning.
    */
  private object ConditionalOnWithinWorld extends LazyLogging {
    def executeIfValid(location:Point, world:World)(f: => Unit): Unit = {
      if(location within world) {
        f
      } else {
        logger.warn(
          s"Ignored as requested location x:${location.x}, y:${location.y} is outside the bounds of the " +
          s"world x:${world.xAxisBounds.min}..${world.xAxisBounds.max}, y:${world.yAxisBounds.min}..${world.yAxisBounds.max}")
      }
    }
  }

  /**
    * A command implementation which will move a robot one position in two dimensional space relative
    * to it's current orientation. If no robot exists in the world, the command is ignored
    */
  private case object Move extends Command with Movement {
    override def execute(world:World):Unit = ConditionalOnRobot.executeIfPresent(world.robot) {
      world.robot match {
        case Some(actual) =>
          val location = actual.orientation match {
            case Orientation.NORTH | Orientation.SOUTH => actual.location.copy(
              actual.location.x,
              reposition(actual.orientation, actual.location.y)
            )
            case Orientation.EAST  | Orientation.WEST  => actual.location.copy(
              reposition(actual.orientation, actual.location.x),
              actual.location.y
            )
          }

          ConditionalOnWithinWorld.executeIfValid(location, world) {
            move(location, actual)
          }

        case None => // ignore
      }
    }

    /**
      * Determine the new axis position for a move in accordance with the passed orientation. Assumes
      * the axis position is relevant to the orientation. Can provide axis locations which may be outside of
      * the bounds of a world.
      *
      * @param orientation    An orientation
      * @param axisIndex      An axis position
      * @return               The new axis position
      */
    private def reposition(orientation:Orientation, axisIndex:Int): Int = {
      orientation match {
        case Orientation.NORTH | Orientation.EAST => axisIndex + 1
        case Orientation.SOUTH | Orientation.WEST => axisIndex - 1
      }
    }
  }

  /**
    * A command implementation which will either:
    * - Place a new robot within the world provided the location exists within the two dimensional space of the world
    * - Move an existing robot within the world provided the location exists within the two dimensional space of the world
    * - Ignore the command regardless of the existence of a robot if the location is outside of the two dimensional
    *   space of the world
    *
    * @param location     A point within two dimensional space
    * @param orientation  An orientation
    */
  private case class Place(location:Point, orientation:Orientation) extends Command with Movement with LazyLogging {
    override def execute(world:World): Unit = ConditionalOnWithinWorld.executeIfValid(location, world) {
      world.robot match {
        case Some(actual) =>
          move(location, actual)
          actual.orientation = orientation
        case None =>
          // robot doesn't exist yet, create and add to the simulation
          val robot = Robot(location, orientation)
          world.robot = Option(robot)
      }
    }
  }

  /**
    * A command implementation which will report on the location and orientation of a robot within a two
    * dimensional space
    */
  private case object Report extends Command {
    override def execute(world:World): Unit = ConditionalOnRobot.executeIfPresent(world.robot) {
      world.robot match {
        case Some(actual) => Console.println(s"${actual.location.x},${actual.location.y},${actual.orientation}")
        case None => // ignore
      }
    }
  }

  /**
    * A command implementation which will re-orient a robot within a world, should a robot be
    * present within the world.
    *
    * @param direction  A direction used to determine the new orientation of a robot
    */
  private case class Turn(direction:Direction) extends Command with Reorientation {
    override def execute(world:World): Unit = ConditionalOnRobot.executeIfPresent(world.robot) {
      world.robot = Option(reorient(world.robot.get, direction))
    }
  }

  /**
    * A trait which allows for constrained movement of a robot within the bounds of the two dimensional
    * space available within the provided world.
    */
  sealed trait Movement extends LazyLogging {
     def move(location:Point, robot: Robot): Unit = {
      robot.location = location
    }
  }

  /**
    * A trait which allows for re-orientation of a robot entity in accordance to a provided Direction
    */
  sealed trait Reorientation {
    private val values  = Orientation.values().map(o => o.index() -> o).toMap // map orientation values by index
    private val indices = Orientation.values().map(o => o.index())
    private val bounds  = indices.min to indices.max // build a bounded range based of the enum indexes (assumes they are contiguous)

    /**
      * Reorient a robot based off of the passed direction.
      *
      * eg.
      *
      * The current orientation is NORTH
      * The request is to turn LEFT
      * The new orientation shall be WEST
      *
      * @param robot        A robot
      * @param direction    A turning direction (LEFT or RIGHT)
      * @return             The robot, now re-orientated
      */
    def reorient(robot: Robot, direction:Direction): Robot = {
      val idx = robot.orientation.index()

      robot.copy(
        robot.location,
        direction match {
          case Direction.LEFT =>  if(!bounds.contains(idx - 1)) values(bounds.last)  else values(idx - 1)
          case Direction.RIGHT => if(!bounds.contains(idx + 1)) values(bounds.start) else values(idx + 1)
        }
      )
    }
  }

  def move():Command = Move
  def place(location:Point, orientation: Orientation):Command = Place(location, orientation)
  def report():Command = Report
  def turn(direction: Direction):Command = Turn(direction)
}

/**
  * A command which can be executed against the world
  */
trait Command {
  def execute(world:World): Unit
}
