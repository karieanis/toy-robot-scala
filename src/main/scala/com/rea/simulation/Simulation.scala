package com.rea.simulation

import com.rea.simulation.model.mutable.World
import com.rea.simulation.parser.{ParseException, ParseTools, SimulationCommandVisitor}
import com.typesafe.scalalogging.LazyLogging
import org.antlr.v4.runtime.misc.ParseCancellationException

/**
  * Application which simulates the placement, movement, orientation and state reporting of a robot entity
  * within a simulated 5 x 5 world space.
  */
object Simulation extends App with LazyLogging {
  val iterator   = Iterator.continually(scala.io.StdIn.readLine).takeWhile(input => input != null)
  val world      = World(5, 5)
  val visitor    = SimulationCommandVisitor()

  // while stdin has not been closed
  while(iterator.hasNext) {
    try {
      val command = iterator.next()
      val tree    = ParseTools.parse(command) // attempt to parse an AST

      visitor.visit(tree).execute(world) // visit the AST, returning a command and executing against the simulation world
    } catch {
      case pce: ParseCancellationException => logger.warn("Invalid command", pce.getCause)
      case pe:  ParseException => logger.warn(s"Parsing exception ${pe.getMessage}", pe.getCause)
      case t:   Throwable => logger.error("An unexpected error has occurred", t.getCause)
    }
  }
}
